#!/bin/bash

GREEN='\033[0;32m'
NC='\033[0m'

echo "==============================================="
echo " Stopping all Port-Forwarding processes..."
echo "==============================================="

# Find and kill kubectl port-forward processes more aggressively
# Also find processes by ports just in case
pkill -9 -f "kubectl port-forward"
lsof -ti :9001,10080,5601,9090,3000 | xargs kill -9 2>/dev/null

echo -e "${GREEN}Successfully stopped and cleaned up all port-forwarding.${NC}"
