#!/bin/sh

NAME=kanban-web

echo "Stopping $NAME..."
lxc-stop --name $NAME

echo "Destroying $NAME..."
lxc-destroy --name $NAME

echo "Remaining virtual-machines..."
lxc-list