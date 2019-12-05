#!/bin/bash
#####SET_VER#####
BASEDIR=/home/work/work
SER_NAME=$1
SER_BASE_R_TMP=${BASEDIR}/${SER_NAME}
SER_BASE_R="${SER_BASE_R_TMP//\//\\/}"
#####NO_NEED_2_SET#####
TOMCAT_NAME=$2
SER_BASE=$BASEDIR/$SER_NAME

#####START#####
echo start
mkdir -p $SER_BASE
if [ ! -d "$BASEDIR/$TOMCAT_NAME" ]; then
 echo do not exist $BASEDIR/$TOMCAT_NAME
 exit 0
else
 $BASEDIR/$TOMCAT_NAME/bin/stop_tomcat.sh
 exit $?
fi
