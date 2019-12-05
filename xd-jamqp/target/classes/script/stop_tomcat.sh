#!/bin/bash
chmod 777 {#basedir#}/JAMQP/script/init_env.sh {#basedir#}/JAMQP/script/param.sh
#####SET_VER#####
source {#basedir#}/JAMQP/script/param.sh
#####START#####
echo start
if [ ! -d "$BASEDIR/$TOMCAT_NAME" ]; then
 exit $?
fi
$BASEDIR/$TOMCAT_NAME/bin/stop_tomcat.sh
exit $?
