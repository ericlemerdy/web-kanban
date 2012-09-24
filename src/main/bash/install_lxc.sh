#!/bin/sh

echo 'Installing lxc and dependencies...'
apt-get install lxc bridge-utils debootstrap

echo "Please execute:"
echo " # sudo echo \"cgroup    /sys/fs/cgroup    cgroup    defaults    0    0\" >> /etc/fstab"
echo " # sudo mount -a"

echo 'Checking lxc configuration...'
lxc-checkconfig