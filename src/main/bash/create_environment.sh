#!/bin/sh

NAME=kanban-web
IP=10.0.3.2

echo "Creating $NAME virtual machine based on ubuntu..."
lxc-create --template ubuntu --name $NAME

echo "Set static IP..."
echo "lxc.network.ipv4=$IP/24" | sudo tee --append /var/lib/lxc/$NAME/config

echo "Adding public ssh key to $NAME..."
mkdir --parents --verbose /var/lib/lxc/$NAME/rootfs/home/ubuntu/.ssh
cat ~/.ssh/id_rsa.pub | sudo tee --append /var/lib/lxc/$NAME/rootfs/home/ubuntu/.ssh/authorized_keys

echo "Starting $NAME..."
lxc-start --daemon --name $NAME

echo "type: \"# ssh ubuntu@$IP\" to enter the machine"