#!/usr/bin/env bash

REPOSITORY=/home/ec2-user/BookLoanSystem
cd $REPOSITORY

APP_NAME=BookLoanSystem
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep 'SNAPSHOT.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 종료할것 없음."
else
  echo "> kill -9 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> $JAR_PATH 배포"
#nohup java -jar $JAR_PATH > /dev/null 2> /dev/null < /dev/null &
nohup java -jar -Dspring.profiles.active=common,dev -Dserver.port=9000 $JAR_PATH > /dev/null 2>&1 &
#nohup java -jar -Dspring.profiles.active=common,dev -Dserver.port=9000 $JAR_PATH > log.out 2>&1 &

