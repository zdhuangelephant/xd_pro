#!/bin/bash

config_path=`pwd`/config.xml
config_path_bak=`pwd`/../config.xml.bak
pom_path=`pwd`/pom.xml

cp -rf ${config_path} ${config_path_bak}

function normal_echo(){
echo -e '\033[0;35;1m'$1'\033[0m'
}

function success_echo(){
echo -e '\033[0;32;1m'$1'\033[0m'
}

function fail_echo(){
echo -e '\033[0;31;1m'$1'\033[0m'
}

function updateJenkinsJob(){
currentVersionFile=`pwd`/target/checkout/current.version
lastVersionFile=`pwd`/target/checkout/last.version
jobNameFile=`pwd`/target/checkout/job.name
configFile=`pwd`/config.xml

currentVersion=`cat ${currentVersionFile}`
lastVersion=`cat ${lastVersionFile}`
jodName=`cat ${jobNameFile}`

#get_status_code=`curl -w %{http_code} -X GET http://192.168.14.198:8080/jenkins/job/${jodName}/config.xml -o ${configFile}`
#if [ ${get_status_code} -eq 200 ]; then
#success_echo "get ${jodName} config.xml success !!! "
#else
#fail_echo "get ${jodName} config.xml fail ... "
#read -n 1 -p "Press any key to continue..."
#exit 1
#fi

#delete_status_code=`curl -w %{http_code} -X POST http://192.168.14.198:8080/jenkins/job/${jodName}/doDelete`
#if [ ${delete_status_code} -eq 302 ]; then
#success_echo "delete ${jodName} success !!! "
#else
#fail_echo "delete ${jodName} fail ... "
#cp -rf ${config_path_bak} ${config_path}
#rm -rf ${config_path_bak}
#read -n 1 -p "Press any key to continue..."
#exit 1
#fi
#
#echo 'lastversion is ' ${lastVersion}
#echo 'currentversion is ' ${currentVersion}

## 新java ci无需删除就得jenkins job，吕振宇会算出四位版本号
##OLD_IFS='$IFS'
##IFS='-'
##verArray=($lastVersion)
##IFS='$OLD_IFS'
##lastVersion_lastItem=${verArray[2]}
##if [ "$lastVersion_lastItem" = "0" ]; then 
##	delete_status_code=`curl -w %{http_code} -X POST http://192.168.14.198:8080/jenkins/job/${jodName}/doDelete`
##	if [ ${delete_status_code} -eq 302 ]; then
##	success_echo "delete ${jodName} success !!! "
##	else
##	fail_echo "delete ${jodName} fail ... "
##	cp -rf ${config_path_bak} ${config_path}
##	rm -rf ${config_path_bak}
##	read -n 1 -p "Press any key to continue..."
##	exit 1
##	fi
##
##	echo 'lastversion is ' ${lastVersion}
##	echo 'currentversion is ' ${currentVersion}
##	
##	echo 'trunk release, need to create jenkins job'
##	sed -i "s/<defaultValue>{#version#}</<defaultValue>${currentVersion}</g" ${configFile}
##	create_status_code=`curl -o /dev/null -w %{http_code} -H "Content-Type:text/xml" -s --data "@config.xml" "http://192.168.14.198:8080/jenkins/createItem?name=${jodName}"`
##	if [ ${create_status_code} -eq 200 ]; then
##		success_echo "create ${jodName} success !!! "
##	else
##		fail_echo "create ${jodName} fail ... "
##		cp -rf ${config_path_bak} ${config_path}
##		rm -rf ${config_path_bak}
##		read -n 1 -p "Press any key to continue..."
##		exit 1
##	fi
##else
##	echo 'branch relase, need not to create jenkins job'
##fi
#update_status_code=`curl -w %{http_code} -X POST http://192.168.14.198:8080/jenkins/job/${jodName}/config.xml --data-binary "@config.xml"`
#if [ ${update_status_code} -eq 200 ]; then
#success_echo "update ${jodName} config.xml success !!! "
#else
#fail_echo "update ${jodName} config.xml fail ... "
#read -n 1 -p "Press any key to continue..."
#exit 1
#fi

return 0
}

function updateVersion(){
location=`pwd`
cd ..
touch version
touch allVersion
cd ${location}
grep '<version>' ./pom.xml > ./../allVersion 
head -1 ./../allVersion > ./../version

versionNumber=`cat ./../version | sed 's/.*<version>//g'`
versionNumber=`echo ${versionNumber} | sed 's/-SNAPSHOT.*//g'`
#echo old version: ${versionNumber}

firstNumber=`echo ${versionNumber} | awk -F'.' '{print $1}'`
secondNumber=`echo ${versionNumber} | awk -F'.' '{print $2}'`
#thirdNumber=`echo ${versionNumber} | awk -F'.' '{print $3}'`
let secondNumber++
cd ..
rm -rf ./version
rm -rf ./allVersion
cd ${location}
versionNumber=${firstNumber}.${secondNumber}.0-SNAPSHOT
echo ${versionNumber} > version.log
}

normal_echo "==> start release ... "
updateVersion
developVersion=`cat version.log`
rm -rf version.log

#if [ $# -gt 0 ]; then
#developVersion=$1
#fi

mvn release:prepare -DautoVersionSubmodules=true  -DdevelopmentVersion=${developVersion}
prepare_release_status=$?
if [ $prepare_release_status -eq 0 ]; then
	mvn release:perform
	perform_release_status=$?
	if [ $perform_release_status -eq 0 ]; then	
	#updateJenkinsJob
	success_echo "==>release success !!!"
	else
	fail_echo "==> execute release:perform fail, please check ... "
	fi
else
	mvn release:rollback
	fail_echo "==> execute release:prepare fail, please check ... "
fi

cp -rf ${config_path_bak} ${config_path}
rm -rf ${config_path_bak}

read -n 1 -p "Press any key to continue..."

exit 0

