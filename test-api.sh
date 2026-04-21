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

    if [[ "$response" =~ $expected ]]; then
        echo -e "${GREEN}PASS${NC} (Response: $response)"
    else
        echo -e "${RED}FAIL${NC}"
        echo "  Expected to match: $expected"
        echo "  Actual response:   $response"
        return 1
    fi
}

echo "==============================================="
echo " Starting API Integration Tests"
echo "==============================================="

# 1. Direct Access Tests (Zombie Listener)
echo -e "\n[1. Direct Access Tests (Port 10080)]"
test_api "$DIRECT_URL" "GET"    "/api/cache/direct-user" "Not Found"
test_api "$DIRECT_URL" "POST"   "/api/cache"             "Direct-Value" '{"key": "direct-user", "value": "Direct-Value"}'
test_api "$DIRECT_URL" "GET"    "/api/cache/direct-user" "Direct-Value"

# 2. Gateway Access Tests (Gateway Server)
echo -e "\n[2. Gateway Access Tests (Port 9001 -> /zombie/**)]"
test_api "$GATEWAY_URL" "GET"    "/zombie/api/cache/gateway-user" "Not Found"
test_api "$GATEWAY_URL" "POST"   "/zombie/api/cache"              "Gateway-Value" '{"key": "gateway-user", "value": "Gateway-Value"}'
test_api "$GATEWAY_URL" "GET"    "/zombie/api/cache/gateway-user" "Gateway-Value"
test_api "$GATEWAY_URL" "GET"    "/zombie/api/scoped-value/test" "Processed with REQ-"

echo -e "\n==============================================="
echo " Tests Completed"
echo "==============================================="
