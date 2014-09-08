#!/bin/bash
# Grabs and kill a process from the pidlist that has the specified string

pid=`ps aux | grep -m 1 maritimecloud-server-0.1.6 | awk '{print $2}'`
kill -9 $pid