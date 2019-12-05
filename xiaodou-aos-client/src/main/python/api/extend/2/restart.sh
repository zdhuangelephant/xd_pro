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
echo start
echo mkdir $BASEDIR
mkdir -p $BASEDIR
cd $BASEDIR
echo mkdir $BASEDIR/$SER_NAME
mkdir -p $SER_NAME
cd $SER_NAME
rm -rf conf/ 
echo ftp down start
wget  -t 1  --timeout=8 -nH -m --ftp-user=$USERNAME --ftp-password=$PASSWORD  ftp://$REMOTEHOST/$FILE_ADRESS/
cp -r $FILE_ADRESS/conf/ $SER_BASE/
rm -rf $FILE_ADRESS/
echo ftp down success
echo end
