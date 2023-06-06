#!/bin/bash
#https://github.com/cloudbees/cloudbees-update-center-plugin/blob/7691ec002a224762baa9c935d374b0a2059c4475/plugin/src/main/java/com/cloudbees/plugins/updatecenter/sources/JenkinsEnterpriseUpdateSource.java#L169-L183
set -x
PLUGIN=$1
VERSION=$2
UC=$3
if [ -z "$PLUGIN" ]
then
    echo 'Usage: uc-grep short-name [core-version [https://jenkins-updates.cloudbees.com/update-center/envelope-core-mm/]]'
    exit 1
fi
if [ -z "$VERSION" ]
then
    VERSION=2.375.2
fi
if [ -z "$UC" ]
then
    UC=https://updates.jenkins-ci.org/
fi
curl -sL "${UC}update-center.json?version=$VERSION" | head -n-1 | tail -n+2 | jq -r '.plugins | .["'$PLUGIN'"] | .version'