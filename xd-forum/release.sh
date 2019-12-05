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
thirdNumber=`echo ${versionNumber} | awk -F'.' '{print $3}'`
let secondNumber++
cd ..
rm -rf ./version
rm -rf ./allVersion
cd ${location}
versionNumber=${firstNumber}.${secondNumber}.${thirdNumber}-SNAPSHOT
echo ${versionNumber} > version.log
}

normal_echo "==> start release ... "
updateVersion
developVersion=`cat version.log`
rm -rf version.log

mvn release:prepare -DautoVersionSubmodules=true  -DdevelopmentVersion=${developVersion}
prepare_release_status=$?
if [ $prepare_release_status -eq 0 ]; then
	mvn release:perform
	perform_release_status=$?
	if [ $perform_release_status -eq 0 ]; then	
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

