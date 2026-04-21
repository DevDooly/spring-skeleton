#!/bin/bash

# Port Configurations
GATEWAY_PORT=9001
ZOMBIE_PORT=10080
KIBANA_PORT=5601

GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m'

echo "==============================================="
echo " Setting up Project Port-Forwarding"
echo "==============================================="

# Function to check port and start forwarding
start_forward() {
    local service=$1
    local local_port=$2
    local remote_port=$3
    local namespace=$4

    if lsof -i :$local_port > /dev/null; then
        echo -e "${YELLOW}[SKIP]${NC} Port $local_port is already in use for $service."
    else
        echo -e "${GREEN}[START]${NC} Forwarding $service ($local_port -> $remote_port)..."
        kubectl port-forward -n $namespace svc/$service $local_port:$remote_port > /dev/null 2>&1 &
        sleep 2
        if lsof -i :$local_port > /dev/null; then
            echo -e "       Success!"
        else
            echo -e "${RED}[ERROR]${NC} Failed to start forwarding for $service."
        fi
    fi
}

# 1. API Gateway
start_forward "gateway-server" $GATEWAY_PORT 80 "default"

# 2. Zombie Listener (Direct Access)
start_forward "zombie-listener" $ZOMBIE_PORT 80 "default"

# 3. Kibana (Logs)
start_forward "kibana" $KIBANA_PORT 5601 "kube-system"

echo -e "\n==============================================="
echo " Active Port-Forwarding Processes:"
ps aux | grep "kubectl port-forward" | grep -v grep
echo "==============================================="
echo -e "${YELLOW}To stop all forwarding, run: ./stop-port-forward.sh${NC}"
