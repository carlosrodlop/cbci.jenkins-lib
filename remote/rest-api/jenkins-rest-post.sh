#!/bin/sh

source ../.env

cmd="curl --user '${JENKINS_USER_ID}:${JENKINS_API_TOKEN}' -XPOST '${JENKINS_URL}$@'"
echo "Command:"
echo "\t${cmd}"
echo "Response:"
eval $cmd
