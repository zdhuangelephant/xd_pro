#!/bin/bash
BASEDIR=/home/work/car
mkdir -p $BASEDIR
export JAVA_HOME=/home/work/car/java
export JRE_HOME=/home/work/car/java/jre
export PATH=$PATH:$JAVA_HOME/bin
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar:$JRE_HOME/lib/rt.jar
cd $BASEDIR
mkdir -p carms
#mv carms.war carms
cd carms
jar -xvf carms.war
#unalias cp
cp -rf $BASEDIR/carms/conf $BASEDIR/carms/WEB-INF/classes/
rm carms.war
/home/work/car/tomcat_8091/bin/start_tomcat.sh &>/dev/null
#/home/work/car/tomcat/bin/startup.sh
exit $?
