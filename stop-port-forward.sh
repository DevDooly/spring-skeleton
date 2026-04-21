#!/bin/bash

GREEN='\033[0;32m'
NC='\033[0m'

echo "==============================================="
echo " Stopping all Port-Forwarding processes..."
echo "==============================================="

# Find and kill kubectl port-forward processes
pkill -f "kubectl port-forward"

if [ $? -eq 0 ]; then
    echo -e "${GREEN}Successfully stopped all port-forwarding.${NC}"
else
    echo "No active port-forwarding processes found."
fi
