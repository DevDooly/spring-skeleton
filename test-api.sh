#!/bin/bash

# Configuration
DIRECT_URL="http://127.0.0.1:10080"
GATEWAY_URL="http://127.0.0.1:9001"
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Helper function for testing
# Usage: test_api <base_url> <method> <path> <expected_pattern> [json_data] [auth_token]
test_api() {
    local base_url=$1
    local method=$2
    local path=$3
    local expected=$4
    local data=$5
    local token=$6
    
    echo -n "Testing $method $base_url$path ... "
    
    local curl_opts=("-s" "-X" "$method")
    if [ -n "$data" ]; then
        curl_opts+=("-H" "Content-Type: application/json" "-d" "$data")
    fi
    if [ -n "$token" ]; then
        curl_opts+=("-H" "Authorization: Bearer $token")
    fi

    # Retry up to 3 times for network stability
    local response=""
    for i in {1..3}; do
        response=$(curl "${curl_opts[@]}" "$base_url$path" | tr -d '\r' | xargs)
        if [ -n "$response" ] || [ "$method" == "DELETE" ]; then break; fi
        sleep 2
    done

    if [[ "$response" =~ $expected ]]; then
        echo -e "${GREEN}PASS${NC} (Res: ${response:0:40}...)"
    else
        echo -e "${RED}FAIL${NC}"
        echo "  Expected: $expected"
        echo "  Actual  : [$response]"
    fi
    sleep 1
}

echo "==============================================="
echo " Starting API Integration Tests with AUTH"
echo "==============================================="

# 1. Login to get Token with Retry
echo -e "\n[1. Authentication]"
echo -n "Logging in as admin ... "

TOKEN=""
for i in {1..5}; do
    TOKEN=$(curl -s -X POST "$GATEWAY_URL/auth/login" \
         -H "Content-Type: application/json" \
         -d '{"username":"admin", "password":"admin123"}' | tr -d '\r' | xargs)
    if [ -n "$TOKEN" ] && [[ ! "$TOKEN" =~ "error" ]] && [[ ! "$TOKEN" =~ "html" ]]; then
        break
    fi
    sleep 3
done

if [ -n "$TOKEN" ]; then
    echo -e "${GREEN}SUCCESS${NC} (Token acquired)"
else
    echo -e "${RED}FAILED${NC} (Could not get token after retries)"
    exit 1
fi

ID=$RANDOM

# 2. Unauthorized Test
echo -e "\n[2. Unauthorized Access Check (Should FAIL/401)]"
echo -n "Testing without token ... "
RES_CODE=$(curl -s -o /dev/null -w "%{http_code}" "$GATEWAY_URL/zombie/api/cache/test")
if [ "$RES_CODE" == "401" ]; then
    echo -e "${GREEN}PASS${NC} (Correctly rejected with 401)"
else
    echo -e "${RED}FAIL${NC} (Expected 401 but got $RES_CODE)"
fi

# 3. Gateway Access Tests (Protected)
echo -e "\n[3. Gateway Access Tests (With Token)]"
test_api "$GATEWAY_URL" "DELETE" "/zombie/api/cache/gw-user-$ID" "" "" "$TOKEN"
test_api "$GATEWAY_URL" "GET"    "/zombie/api/cache/gw-user-$ID" "Not Found" "" "$TOKEN"
test_api "$GATEWAY_URL" "POST"   "/zombie/api/cache"             "gw-val-$ID" "{\"key\": \"gw-user-$ID\", \"value\": \"gw-val-$ID\"}" "$TOKEN"
test_api "$GATEWAY_URL" "GET"    "/zombie/api/cache/gw-user-$ID" "gw-val-$ID" "" "$TOKEN"
test_api "$GATEWAY_URL" "GET"    "/zombie/api/scoped-value/test" "Processed with REQ-" "" "$TOKEN"

echo -e "\n==============================================="
echo " Tests Completed"
echo "==============================================="
