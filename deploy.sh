#!/bin/bash

echo "> 현재 실행 중인 Docker 컨테이너 pid 확인" >> /home/ec2-user/deploy.log
JAVA_PID=$(sudo docker container ls -q --filter "ancestor=workoutwith")

if [ -z $JAVA_PID ]
then
  echo "> 현재 구동중인 JAVA Docker 컨테이너가 없으므로 종료하지 않습니다." >> /home/ec2-user/deploy.log
else
  echo "> sudo docker stop $JAVA_PID"   # 현재 구동중인 Docker 컨테이너가 있다면 모두 중지
  sudo docker stop $JAVA_PID
  sleep 5
fi


## 이전 버젼의 java Docker Image 제거
docker images -q | grep -v -F -f <(docker ps --format "{{.Image}}" | awk -F: '{print $1}') | xargs -r docker rmi

##경로설정
cd /home/ec2-user/apps/workoutwith

##도커 이미지 생성 및 실행
sudo docker build -t workoutwith .
sudo docker run -d -p 8080:8080 workoutwith
