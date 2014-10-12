#!/bin/bash

SERVER_PATH="/home/test/tppi"
LOG_PATH="$SERVER_PATH/plugins/Roc/Roc.log"
SCRIPT_LOG_PATH="$SERVER_PATH/roc.log"
PID_PATH="$SERVER_PATH/roc.pid"

cd $SERVER_PATH

function checkLog() {
	LAST_LINE=$(awk '/./{line=$0} END{print line}' $LOG_PATH)
	LOGLINE=(${LAST_LINE//-/ })
	
	LASTTYPE=${LOGLINE[0]}
	LASTSTAMP=${LOGLINE[1]}
	
	TIMESTAMP=$(date +%s)
	LOGSTAMP=$LASTSTAMP
	DIFF=$(($TIMESTAMP-$LOGSTAMP))
}

function startServer() {
	echo "1-"$(date +%s) > $LOG_PATH
	killall -9 java
    sleep 2
	screen -dmS mcserver java -Xmx3G -Xms1G -XX:PermSize=512M -XX:+UseCMSCompactAtFullCollection -XX:+UseParNewGC -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+AggressiveOpts -XX:-OmitStackTraceInFastThrow -XX:UseSSE=8 -jar ftbserver.jar nogui
}

if [ ! -f $PID_PATH ]; then
	echo $$ > $PID_PATH
else
	echo "$(date) - Script running, abort!"
	exit
fi

if [ ! -f $LOG_PATH ]; then
	echo "$(date) - Starting Server!"
	startServer
	rm $PID_PATH
	exit
fi

checkLog

if [[ $LASTTYPE -eq "0" ]]
then
	checkLog
	echo "$(date) - Server down since $DIFF sec!"
	echo "$(date) - Server Stopped - restarting in 20"
	sleep 20
	echo "$(date) - Restarting now!"
	startServer
elif [[ $LASTTYPE -eq "1" ]]
then
	checkLog
	echo "$(date) - Server started $DIFF sec before!"
	#sleep 130
	#if [[ $DIFF -gt 150 ]]
	if [[ $(($DIFF-150)) -gt 0 ]]
	then
		echo "$(date) - Seems crashed, restart!"
		startServer
	fi
else
	checkLog
	if [[ $DIFF  -gt 30 ]]
	then
		echo "$(date) - Server down since $DIFF sec!"
		echo "$(date) - Restarting now!"
		startServer
	else
		echo "$(date) - Server running"
	fi
fi

rm $PID_PATH

