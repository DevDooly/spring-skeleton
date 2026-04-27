#!/bin/bash

# Configuration
DIRECT_URL="http://127.0.0.1:10080"
GATEWAY_URL="http://127.0.0.1:9001"
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Helper function for testing
test_api() {
    local base_url=$1
    local method=$2
    local path=$3
    local expected=$4
    local data=$5
    local token=$6
    
    echo -n "Testing $method $base_url$path ... "
    
    local curl_opts=("-s" "-X" "$method" "-w" "\n%{http_code}")
    if [ -n "$data" ]; then
        curl_opts+=("-H" "Content-Type: application/json" "-d" "$data")
    fi
    if [ -n "$token" ]; then
        local clean_token=$(echo "$token" | tr -d '[:space:]')
        curl_opts+=("-H" "Authorization: Bearer $clean_token")
    fi

    # Capture result (Body + HTTP Code)
    local full_res=$(curl "${curl_opts[@]}" "$base_url$path")
    local http_code=$(echo "$full_res" | tail -n1)
    local response=$(echo "$full_res" | head -n -1 | tr -d '\r' | xargs)

    if [[ "$response" =~ $expected ]] || ([ "$method" == "DELETE" ] && [ "$http_code" == "200" ]); then
        echo -e "${GREEN}PASS${NC} (Status: $http_code)"
    else
        echo -e "${RED}FAIL${NC}"
        echo "  Status  : $http_code"
        echo "  Expected: $expected"
        echo "  Actual  : [$response]"
    fi
    sleep 1
}

echo "==============================================="
echo " Starting API Integration Tests with AUTH"
echo "==============================================="

# 1. Login with Retry
echo -e "\n[1. Authentication]"
echo -n "Logging in as admin ... "
TOKEN=""
for i in {1..5}; do
    TOKEN=$(curl -s -X POST "$GATEWAY_URL/auth/login" -H "Content-Type: application/json" -d '{"username":"admin", "password":"admin123"}' | tr -d '\r' | xargs)
    if [ -n "$TOKEN" ] && [[ ! "$TOKEN" =~ "error" ]] && [[ ! "$TOKEN" =~ "html" ]]; then break; fi
    sleep 2
done

if [ -n "$TOKEN" ]; then
    echo -e "${GREEN}SUCCESS${NC}"
else
    echo -e "${RED}FAILED${NC} (Could not get token)"
fi

ID=$RANDOM

# 2. Unauthorized Test
echo -e "\n[2. Unauthorized Access Check]"
test_api "$GATEWAY_URL" "GET" "/zombie/api/cache/test" "401" "" ""

# 3. Gateway Access Tests
echo -e "\n[3. Gateway Access Tests (Protected)]"
if [ -n "$TOKEN" ]; then
    test_api "$GATEWAY_URL" "DELETE" "/zombie/api/cache/gw-user-$ID" "" "" "$TOKEN"
    test_api "$GATEWAY_URL" "GET"    "/zombie/api/cache/gw-user-$ID" "Not Found" "" "$TOKEN"
    test_api "$GATEWAY_URL" "POST"   "/zombie/api/cache"             "gw-val-$ID" "{\"key\": \"gw-user-$ID\", \"value\": \"gw-val-$ID\"}" "$TOKEN"
    test_api "$GATEWAY_URL" "GET"    "/zombie/api/cache/gw-user-$ID" "gw-val-$ID" "" "$TOKEN"
else
    echo -e "${YELLOW}Skipping protected tests due to missing token.${NC}"
fi

echo -e "\n==============================================="
echo " Tests Completed"
echo "==============================================="
