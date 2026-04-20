#!/bin/bash

# Configuration
BASE_URL="http://localhost:10080"
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Helper function for testing
# Usage: test_api <method> <path> <expected_pattern> [json_data]
test_api() {
    local method=$1
    local path=$2
    local expected=$3
    local data=$4
    
    echo -n "Testing $method $path ... "
    
    local response
    if [ "$method" == "POST" ]; then
        response=$(curl -s -X "$method" "$BASE_URL$path" -H "Content-Type: application/json" -d "$data")
    else
        response=$(curl -s -X "$method" "$BASE_URL$path")
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
echo " Starting API Integration Tests (Port 10080)"
echo "==============================================="

# 1. Caffeine Cache Tests
echo -e "\n[1. Caffeine Cache Tests]"
test_api "GET"    "/api/cache/test-user" "Not Found"
test_api "POST"   "/api/cache"         "John-Doe" '{"key": "test-user", "value": "John-Doe"}'
test_api "GET"    "/api/cache/test-user" "John-Doe"
test_api "DELETE" "/api/cache/test-user" ""
test_api "GET"    "/api/cache/test-user" "Not Found"

# 2. Scoped Value & Virtual Thread Tests
echo -e "\n[2. Scoped Value & Virtual Thread Tests]"
test_api "GET" "/api/scoped-value/test" "Processed with REQ-"
test_api "GET" "/api/scoped-value/structured-task" "Main Result: Processed with STRUCT-.*SubResult: Done"

echo -e "\n==============================================="
echo " Tests Completed"
echo "==============================================="
