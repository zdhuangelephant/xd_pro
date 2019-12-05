#!/bin/bash

for i in `ls -al /home/work/xiaodou |awk '{if($9~/tomcat/){print $9}}'`
do
    #echo '`find /home/work/xiaodou/'$i'/logs -ctime +7 | xargs -n 1 rm -rf`'
    `find /home/work/xiaodou/$i/logs -ctime +7 | xargs -n 1 rm -rf`
    #echo '`find /home/work/xiaodou/'$i'/logs -size +1000000c | xargs -n 1 rm -rf`'
    `find /home/work/xiaodou/$i/logs -size +1000000c | xargs -n 1 rm -rf`
done
