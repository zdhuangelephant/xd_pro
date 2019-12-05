#!/bin/bash
SQL_PATH=/home/work/xiaodou/crontab/message
SQL_FILE=create_body.sql
BODY_TABLE_1=`date "+_%y_%m_%d" -d "1 day"`
BODY_TABLE_2=`date "+_%y_%m_%d" -d "2 day"`
BODY_TABLE_3=`date "+_%y_%m_%d" -d "3 day"`
BODY_TABLE_4=`date "+_%y_%m_%d" -d "4 day"`
BODY_TABLE_5=`date "+_%y_%m_%d" -d "5 day"`
cat $SQL_PATH/$SQL_FILE | sed "s/{#prefix1#}/$BODY_TABLE_1/g" | sed "s/{#prefix2#}/$BODY_TABLE_2/g" |sed "s/{#prefix3#}/$BODY_TABLE_3/g" |sed "s/{#prefix4#}/$BODY_TABLE_4/g" |sed "s/{#prefix5#}/$BODY_TABLE_5/g" > $SQL_PATH/new_body$BODY_TABLE_1.sql
mysql -uxddev -pxddev@xiaodou message <<EOF
 source $SQL_PATH/new_body$BODY_TABLE_1.sql
EOF
rm -rf $SQL_PATH/new_body$BODY_TABLE_1.sql
