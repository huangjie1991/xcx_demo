#!/bin/sh
. /etc/profile
. ~/.bash_profile
#=====================

stillRunning=$(ps -ef |grep "xcx" |grep -v "grep")
if [ "$stillRunning" ] ; then
pid=`ps -ef | grep xcx.jar | grep -v "grep" | awk '{print $2}'`
echo "pid=$pid"
        if [ -n "$pid" ]
        then
        kill -9 $pid
        fi
fi
# 打开jar包所在文件夹
cd 
nohup java -Xms512m -Xmx1024m -jar xcx-0.0.1 --spring.profiles.active=test &
sleep 10s
