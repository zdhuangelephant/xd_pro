#!/usr/bin/env bash
BASE=`pwd`
DIR_TMP=.runtime
PID_FILE=$DIR_TMP/pid2.file
RETVAL=0
mkdir -p $DIR_TMP

function copy_conf
{
    if [ -f config.ini ];then
        cp config.ini $BASE/../src/main/resources/conf
    fi
}

function status
{
    PID=`cat $PID_FILE 2>/dev/null`
    if [ -f $PID_FILE ] && [ "$PID" != "" ] && [  -d /proc/$PID ];then
        echo "Instance is [RUNNING]."
        RETVAL=0
    else
        echo "Instance is [STOPPED]."
        RETVAL=1
    fi
}

function start
{
    status > /dev/null 2>&1
    if [ $RETVAL -ne 0 ];then
        copy_conf
        nohup python $BASE/../src/main/python/nginx_setup.py > /dev/null 2>&1 &
        echo $! > $PID_FILE
        echo "Instance is [STARTED]."
        RETVAL=$?
    else
        echo "ERR: Instance is already running."
        exit 1
    fi
}

function stop
{
    if [ -f $PID_FILE ];then
        cat $PID_FILE | xargs -n 1 kill -9
    fi
    echo "Instance is [STOPPED]."
    RETVAL=$?
}


#See how we called
case "$1" in
    start)
        start
        ;;
    stop)
        stop
        ;;
    copy)
        copy_conf
        ;;
    restart|reload)
        stop
        sleep 2
        start
        RETVAL=$?
        ;;
    status)
        status
        RETVAL=$?
        ;;
    *)
        echo $"Usage: $0 {start|stop|restart|reload|status}"
        exit 1
esac

exit $RETVAL