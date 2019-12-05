#!/bin/bash
##path##
basedir=
baseop=xd-rjar-test
##server_info##
tempop=._tmpop
jarfile=`ls|grep jar`

function init_env
{
	mkdir -p $basedir/$baseop/$tempop
}

function deploy
{
    cd $basedir/$baseop/$tempop && mv $basedir/$baseop/$jarfile .
    jar -xvf $jarfile > /dev/null && /bin/rm -rf $jarfile
    cp -rf $basedir/$baseop/conf/* $basedir/$baseop/$tempop/conf/custom/env
    jar -cvf0 $jarfile ./  > /dev/null && mv $jarfile $basedir/$baseop 
    cd $basedir/$baseop && /bin/rm -rf $tempop
    chmod u+x start.sh && ./start.sh restart
}

init_env
deploy
