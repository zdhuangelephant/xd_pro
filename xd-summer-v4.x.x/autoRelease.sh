#!/bin/bash

function normal_echo(){
echo -e '\033[0;35;1m'$1'\033[0m'
}

function success_echo(){
echo -e '\033[0;32;1m'$1'\033[0m'
}

function fail_echo(){
echo -e '\033[0;31;1m'$1'\033[0m'
}
#put snapshot dependencies message with tag into SnapshotsMessageTemp.log.(message like <version>XXXX</version>)
function getSnapshotsMessageTemp(){
#get the number of snapshots
snapshots=`grep -c '.*-SNAPSHOT.*' ./pom.xml`
let snapshots--
normal_echo "number of snapshots:${snapshots}"

if [ $snapshots -gt 0 ]; then
   #get the line number of the snapshot dependency message(groupId,artifactId,version)
   grep -n '.*-SNAPSHOT.*' ./pom.xml | sed 's/:.*//g' > allSnapshotsLineNumber.log 
   tail -n +2 allSnapshotsLineNumber.log >dependencySnapshotsLineNumber.log
   awk '{ print $0-2>>"snapshots.log";print $0-1>>"snapshots.log";print $0>>"snapshots.log"}' dependencySnapshotsLineNumber.log
   awk 'BEGIN{read=1} {if(read){getline a<"snapshots.log";}if(NR==a){read=1;print>"snapshotsMessageTemp.log"}else{read=0}}' pom.xml
   rm -rf allSnapshotsLineNumber.log dependencySnapshotsLineNumber.log snapshots.log
fi
}

#put snapshot dependencies message  into SnapshotsMessage.log.(for each line : groupId artifactId version)
function getSnapshotsMessage(){
if [ -e snapshotsMessageTemp.log ]; then
	counter=0
	cat snapshotsMessageTemp.log | while read row; do
	if [ $counter -eq 0 ]; then
		groupId=`echo $row | sed 's/.*<groupId>//g' | sed 's/<\/groupId>.*//g'`
		let counter++
		echo $groupId
	elif [ $counter -eq 1 ]; then
		artifactId=`echo $row | sed 's/.*<artifactId>//g' | sed 's/<\/artifactId>.*//g'`
		let counter++
		echo $artifactId
	else
		version=`echo $row | sed 's/.*<version>//g' | sed 's/-SNAPSHOT<\/version>.*//g'`
		let counter-=2
		echo $version
		snapshot=${groupId}":"${artifactId}":"${version}
		echo $snapshot >> snapshotsMessage.log
	fi
	done
rm -rf snapshotsMessageTemp.log
	if [ -e snapshotsMessage.log ]; then
		success_echo "==>get snapshots message successfully"
	fi
fi
}

#download the dependent modules
function downloadModule(){
	artifactId=$1
	newUrl=`echo $2 | awk -F/ '{print $1"/"$2"/"$3"/"$4"/"$5"/"$6"/"}'`
	newUrl=$newUrl$artifactId"Trunk"
	normal_echo "==>start download project from $newUrl ..."
	svn checkout $newUrl $artifactId
	downloadResult=$?
	
	if [ $downloadResult -ne 0 ]; then
		newUrl="http://svn.elong.cn/svn/Code/HotelMIS/"$artifactId"Trunk"
		svn checkout $newUrl $artifactId
		downloadResult=$?
	fi
	if [ $downloadResult -ne 0 ]; then
		newUrl="http://svn.elong.cn/svn/Code/ElongOrder/"$artifactId"Trunk"
		svn checkout $newUrl $artifactId
		downloadResult=$?
	fi
	if [ $downloadResult -ne 0 ]; then
		newUrl="http://svn.elong.cn/svn/Code/Pub/"$artifactId"Trunk"
		svn checkout $newUrl $artifactId
		downloadResult=$?
	fi
	if [ $downloadResult -ne 0 ]; then
		newUrl="http://svn.elong.cn/svn/Code/zhilian/"$artifactId"Trunk"
		svn checkout $newUrl $artifactId
		downloadResult=$?
	fi
	if [ $downloadResult -ne 0 ]; then
		newUrl="http://svn.elong.cn/svn/Code/JHotelMIS/"$artifactId"Trunk"
		svn checkout $newUrl $artifactId
		downloadResult=$?
	fi
	
	return $downloadResult
}

#release the snapshots
function releaseSnapshot(){
normal_echo "==>start release $1"
location=`pwd`
cd ..

artifactId=$1"/"
url=$2
#download project form svn if the project isn't in the document
if [ ! -d $artifactId ]; then
	downloadModule $artifactId $url
#	newUrl=`echo $url | awk -F/ '{print $1"/"$2"/"$3"/"$4"/"$5"/"$6"/"}'`
#	newUrl=$newUrl$artifactId"Trunk"
#	normal_echo "==>start download project from $newUrl ..."
#	svn checkout $newUrl $artifactId
	if [ $? -eq 0 ]; then
	   success_echo "Download project successfully !" 
	else
	   fail_echo "Download project failed,please check the svn url of the dependency"
	   read -n 1 -p "Press ctrl+c to stop release"
	fi
fi
cd $artifactId
svn update
sh autoRelease.sh
result=$?
wait
cd $location
return $result
}
#log the release information
function log(){
information="Successfully release : "$1":"$2":"$3
date=`date`
information=$information":"$date
echo $information >> /home/work/workspace/release.log
}
#delete the string '-SNAPSHOT' in the version string of snapshots
function cutSnapshot(){
echo cut snapshot
grep -n '.*-SNAPSHOT.*' ./pom.xml | sed 's/:.*//g' | tail -n +2 > snapshotsLineNumber.log
awk 'BEGIN{read=1}
 {if(read){getline a<"snapshotsLineNumber.log";}
 if(NR==a){read=1;sub(/-SNAPSHOT/,"",$0);}
 else{read=0};print >> "pom.xml.bak"}' pom.xml
rm -rf snapshotsLineNumber.log
mv pom.xml.bak pom.xml
}

#function main()
getSnapshotsMessageTemp
getSnapshotsMessage

if [ -e snapshotsMessage.log ]; then
	normal_echo "==>start release the snapshot of dependency"
	#echo snapshot dependency exist
	while read line
	do 
		array[n++]="$line"
	done < snapshotsMessage.log

	for row in ${array[@]}
	do

		groupId=`echo $row | awk -F: '{print $1}'`
		artifactId=`echo $row | awk -F: '{print $2}'`
		version=`echo $row | awk -F: '{print $3}'`
		#test whether there are stable version
		url="http://mvn.elong.cn:8081/nexus/service/local/lucene/search?g="$groupId"&a="$artifactId"&v="$version"&collapseresults=true"
		content=`curl $url`
		flag=`echo $content | grep -c '<repositoryPolicy>'`
		#release the package if there is no stable version
		if [ $flag -eq 0 ]; then
		  scmUrl=`grep '<url>' ./pom.xml | tail -n +2 | head -1 | sed 's/.*<url>//g' | sed 's/<\/url>.*//g'` 
		  releaseSnapshot $artifactId $scmUrl
		  if [ $? -eq 0 ]; then
			 log $groupId $artifactId $version
		  else
			fail_echo "failed to release "$artifactId
			exit 1
		  fi
		fi

	done
	echo "dependency release completed,do you want to cut the snapshots in pom.xml?(y or n):"
	read yn
	if [ $yn == "y" ]; then
	cutSnapshot
	else
		exit 1
	fi
	rm -rf snapshotsMessage.log
fi
svn ci pom.xml -m "delete the snapshots in pom.xml"
sh release.sh
result=$?
wait
exit $result
