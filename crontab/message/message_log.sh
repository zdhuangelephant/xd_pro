#!/bin/bash
SQL_PATH=/home/work/xiaodou/crontab/message
SQL_FILE=create_log.sql
LOG_TABLE_1=`date "+_%y_%m_%d" -d "1 day"`
LOG_TABLE_2=`date "+_%y_%m_%d" -d "2 day"`
LOG_TABLE_3=`date "+_%y_%m_%d" -d "3 day"`
LOG_TABLE_4=`date "+_%y_%m_%d" -d "4 day"`
LOG_TABLE_5=`date "+_%y_%m_%d" -d "5 day"`
cat $SQL_PATH/$SQL_FILE | sed "s/{#prefix1#}/$LOG_TABLE_1/g" | sed "s/{#prefix2#}/$LOG_TABLE_2/g" |sed "s/{#prefix3#}/$LOG_TABLE_3/g" |sed "s/{#prefix4#}/$LOG_TABLE_4/g" |sed "s/{#prefix5#}/$LOG_TABLE_5/g" > $SQL_PATH/new_log$LOG_TABLE_1.sql
mysql -uxddev -pxddev@xiaodou message <<EOF
 source $SQL_PATH/new_log$LOG_TABLE_1.sql
EOF
rm -rf $SQL_PATH/new_log$LOG_TABLE_1.sql
