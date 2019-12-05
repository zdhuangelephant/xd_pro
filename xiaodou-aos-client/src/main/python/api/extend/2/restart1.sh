#/bin/bash
#####SET_VER#####

BASEDIR=$1
REMOTEHOST=$6
USERNAME=$7
PASSWORD=$8
SER_NAME=$2
FILE_ADRESS=$3
SER_BASE=$BASEDIR/$SER_NAME
WARADRESS=$4
VERSION=$5
#####START#####
echo start shell
cd $SER_BASE
chmod 777 *.sh
source deploy.sh
echo start shell final

