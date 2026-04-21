#!/bin/bash

# Configuration
DIRECT_URL="http://localhost:10080"
GATEWAY_URL="http://localhost:9001"
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Helper function for testing
# Usage: test_api <base_url> <method> <path> <expected_pattern> [json_data]
test_api() {
    local base_url=$1
    local method=$2
    local path=$3
    local expected=$4
    local data=$5
    
    echo -n "Testing $method $base_url$path ... "
    
    local response
    if [ "$method" == "POST" ]; then
        response=$(curl -s -X "$method" "$base_url$path" -H "Content-Type: application/json" -d "$data")
    else
        response=$(curl -s -X "$method" "$base_url$path")
    fi

    # Trim
    response=$(echo "$response" | xargs)

    if [[ "$response" =~ $expected ]]; then
        echo -e "${GREEN}PASS${NC} (Res: $response)"
    else
        echo -e "${RED}FAIL${NC}"
        echo "  Expected: $expected"
        echo "  Actual  : [$response]"
    fi
    sleep 2 # Prevent port-forward crash
}

echo "==============================================="
echo " Starting API Integration Tests"
echo "==============================================="

ID=$RANDOM
echo -e "\n[1. Direct Access Tests (Port 10080)]"
test_api "$DIRECT_URL" "DELETE" "/api/cache/user-$ID" ""
test_api "$DIRECT_URL" "GET"    "/api/cache/user-$ID" "Not Found"
test_api "$DIRECT_URL" "POST"   "/api/cache"          "val-$ID" "{\"key\": \"user-$ID\", \"value\": \"val-$ID\"}"
test_api "$DIRECT_URL" "GET"    "/api/cache/user-$ID" "val-$ID"

echo -e "\n[2. Gateway Access Tests (Port 9001 -> /zombie/**)]"
test_api "$GATEWAY_URL" "DELETE" "/zombie/api/cache/gw-user-$ID" ""
test_api "$GATEWAY_URL" "GET"    "/zombie/api/cache/gw-user-$ID" "Not Found"
test_api "$GATEWAY_URL" "POST"   "/zombie/api/cache"             "gw-val-$ID" "{\"key\": \"gw-user-$ID\", \"value\": \"gw-val-$ID\"}"
test_api "$GATEWAY_URL" "GET"    "/zombie/api/cache/gw-user-$ID" "gw-val-$ID"
test_api "$GATEWAY_URL" "GET"    "/zombie/api/scoped-value/test" "Processed with REQ-"

echo -e "\n==============================================="
echo " Tests Completed"
echo "==============================================="
