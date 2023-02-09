#!/bin/sh

source ../.env

cmd="curl --user '${JENKINS_USER_ID}:${JENKINS_API_TOKEN}' -XGET '${JENKINS_URL}$@'"
echo "Command:"
echo "\t${cmd}"
echo "Response:"
eval $cmd
