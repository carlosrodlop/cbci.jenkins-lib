#!/bin/sh

source ../.env

echo "[INFO]: Downloading CLI"
wget --user $JENKINS_USER_ID --password $JENKINS_API_TOKEN ${JENKINS_URL}/jnlpJars/jenkins-cli.jar
echo "[INFO]: Runing command"
cmd="java -jar jenkins-cli.jar -auth $JENKINS_USER_ID:$JENKINS_API_TOKEN -logger FINE -s $JENKINS_URL $@"
echo "Command:"
echo "\t${cmd}"
echo "Response:"
eval $cmd
echo "[INFO]: Removing CLI"
rm jenkins-cli.jar
