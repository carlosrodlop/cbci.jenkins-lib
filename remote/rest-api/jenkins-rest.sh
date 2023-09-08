#!/usr/bin/env bash

set -e

HERE="$( cd -P "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# shellcheck source=/dev/null
source "${HERE}/../.env"

# Input

REQUEST_TYPE=
REQUEST_OPERATION=

ARGS=("$@")

usage() {
  cat <<EOM
    Usage:
      $(basename "$0") -t <REQUEST_TYPE> | --type <REQUEST_TYPE>  -o <REQUEST_OPERATION> | --operation <REQUEST_OPERATION> [ -h | --help ]
    Options:
    	-t	--type			Request type: get, post, put, delete.
        -o	--operation	    Request operation
        -h, --help          Show usage.

    Example:
      $(basename "$0") --type -XDELETE --operation projects/projectExample
EOM
    exit 1
}

validate(){
    for i in "${!ARGS[@]}"; do
        arg="${ARGS[${i}]}"
        
        case "${arg}" in
            -t | --type)
                REQUEST_TYPE="${ARGS[$((i + 1))]}"
            ;;
            -o | --operation)
                REQUEST_OPERATION="${ARGS[$((i + 1))]}"
            ;;
            -h | --help)
                usage
            ;;
        esac
    done
}

run (){
    cmd="curl --user '${JENKINS_USER_ID}:${JENKINS_API_TOKEN}' $REQUEST_TYPE '$JENKINS_URL/$REQUEST_OPERATION'"
    echo "Command:"
    echo "${cmd}"
    echo "Response:"
    eval "$cmd"
}

validate
run