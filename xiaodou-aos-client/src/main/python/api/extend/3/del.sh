#/bin/bash
#####SET_VER#####
BASEDIR=$1
SER_NAME=$2
FILE_ADRESS=$3
WARADRESS=$4
TOMCAT_PORT=$5
PREFIX=tomcat_
TOMCAT_BASE=$BASEDIR/$PREFIX$TOMCAT_PORT
PROJECT_BASE=$BASEDIR/$SER_NAME
#####START#####
echo start delShell
echo $TOMCAT_BASE
if [ "$TOMCAT_PORT" == "-" ];then
        TOMCAT_BASE=$PROJECT_BASE
fi
PID=`ps -ef | grep "$TOMCAT_BASE" | grep -v "grep" | awk '{print $2}'`
echo $PID
echo "---------------"
for id in $PID
do
kill -9 $id
done
echo "killed $id"
echo "delPrject"
if [ "$TOMCAT_PORT" != "-" ];then
        rm -rf $TOMCAT_BASE
fi
rm -rf $PROJECT_BASE
echo SUCCESS
