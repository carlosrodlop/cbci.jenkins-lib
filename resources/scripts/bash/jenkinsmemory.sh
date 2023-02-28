#!/bin/bash
####################################################################################
# This script is used to collect data for cbsupport require-data memory
#
# ./$script_name.sh [PID of the Jenkins instance experiencing the Out of Memory Exception issue] (default mode)
# ./$script_name.sh PID 1 of the Jenkins instace experiencing the Out of Memory Exception issue (heap dump mode)
#
# It uses `jcmd` and `jmap` to collect the information, the primary tools used is `jcmd`. Commands available:
#  jcmd $pid GC.class_histogram // jmap -histo $pid
#  jcmd $pid GC.heap_dump "$JENKINS_JMAP_OUTPUT_DIR/heapdump.$pid.hprof" // jmap -dump:live,format=b,file=heapdump.$pid.hprof $pid

# Originally taken from https://s3.amazonaws.com/cloudbees-jenkins-scripts/e206a5-linux/jenkinsjmap.sh
#
# But completely revamped:
# - Now compatible with `jcmd` tool
# - Commands adapted to work with Alpine Linux images.
#   jmap in openjdk8 related apk packages on Alpine Linux has less debugging functionalities, so commands like jmap -heap won't work
#   see: https://github.com/docker-library/openjdk/issues/286
#
# - Simpler code
# - Usage of CBSUPPORT_OUTPUT to easily retrieve the script output from cbsupport
  SCRIPT_VERSION=v2.0.2019.08.21
#####################################################################################

script_name=$(basename "$0" .sh)
print_help ()
{
  cat <<EOM
Unable to find required PID argument. Please rerun the script as follows:

 ${script_name}.sh PID [mode]

    PID:      Jenkins PID
    mode:     0 - generate histo file (default)
              1 - generate histo + heapdump file

    Optional environment vars

      JENKINS_JMAP_OUTPUT_DIR output dir

    Run ${script_name}.sh --help to print help.
EOM
}

# Check if the PID was specified when running the script
if [ $# -eq 0 ]
then
  print_help
  exit 1
fi

#####################################
#pid
mode=0
#####################################

if [ $# -eq 1 ]
then
  if [ "$1" = "--help" ]; then
    print_help
    exit 0
  fi
  pid=$1
  mode=0
elif [ $# -eq 2 ]
then
  pid=$1
  mode=$2
else
  print_help $0
  exit 1
fi

if [ -z "$JENKINS_JMAP_OUTPUT_DIR" ]; then
  JENKINS_JMAP_OUTPUT_DIR="/tmp"
  echo $(date) "Output dir $JENKINS_JMAP_OUTPUT_DIR"
fi

script_validation ()
{
  echo "Script Validation Results"
  echo "Moving to ${JENKINS_JMAP_OUTPUT_DIR}"

  HERE="$(pwd)"
  cd $JENKINS_JMAP_OUTPUT_DIR

  #check if the directory can be written to by the user that is running the script, i.e. jenkins user
  touch testFile.txt 2>/dev/null

  if [ -e testFile.txt ]
  then
    echo "[SUCCESS]: This directory can be written to by the script"
  fi

  if [ ! -e testFile.txt ]
  then
    echo "[ERROR]: This directory cannot be written to by the script. Please either run this script from a directory that can be written to or use the optional environment variable: JENKINS_JMAP_OUTPUT_DIR ."
    exit 1
  fi

  rm -rf testFile.txt

  local jcmd_bin="jcmd"
  local jmap_bin="jmap"

  if [ -n "${JAVA_HOME}" ]; then
    echo "[INFO]: JAVA_HOME is set. Looking for Java Troubleshooting Tools \"${JAVA_HOME}/bin\"."
    jcmd_bin="${JAVA_HOME}/bin/jcmd"
    jmap_bin="${JAVA_HOME}/bin/jmap"
  else
    echo "[INFO]: JAVA_HOME is NOT set. Looking for Java Troubleshooting Tools on the PATH."
  fi
  if ! command -v ${jcmd_bin} >/dev/null 2>&1 && ! command -v ${jmap_bin} >/dev/null 2>&1; then
    echo && echo >&2 "[ERROR]: jcmd or jmap is required but neither was found. Either the full Java JDK is not installed or it is not the path of the user that is running Jenkins." && echo;
    exit 1
  fi

  echo "Moving back to current dir $HERE"
  cd $HERE

}

# Validate
script_validation $0

declare jcmd_bin="jcmd"
declare jmap_bin="jmap"

echo $userPID

if [ -n "$JAVA_HOME" ]; then
  jcmd_bin="${JAVA_HOME}/bin/jcmd"
  jmap_bin="$JAVA_HOME/bin/jmap"
fi

write_histo ()
{
  date >> $2
  if command -v ${jcmd_bin} >/dev/null 2>&1; then
    ${jcmd_bin} $1 GC.class_histogram >> $2
  elif command -v ${jmap_bin} >/dev/null 2>&1; then
    ${jmap_bin} -histo $1 >> $2
  fi
  if [ $? -eq 1 ]; then
    userPID=$(ps -o user= -p $1)
    echo "Command failed: Please run this script as 'root' or using 'sudo -u $userPID'" >&2
    exit 1
  fi

}

write_dump ()
{
  if command -v ${jcmd_bin} >/dev/null 2>&1; then
    ${jcmd_bin} $1 GC.heap_dump "$JENKINS_JMAP_OUTPUT_DIR/$2"
  elif command -v ${jmap_bin} >/dev/null 2>&1; then
    ${jmap_bin} -dump:live,format=b,file="$JENKINS_JMAP_OUTPUT_DIR/$2" $1
  fi
  if [ $? -eq 1 ]; then
    userPID=$(ps -o user= -p $1)
    echo "Command failed: Please run this script as 'root' or using 'sudo -u $userPID'" >&2
    exit 1
  fi
}

tar_file () {
  tar -czvf "$1" "${@:2}"
}

# Begin script and notify the end user
echo $(date) "The $script_name.sh script $SCRIPT_VERSION is starting in mode $mode"

if [ -z "$CBSUPPORT_OUTPUT" ]; then
  CBSUPPORT_OUTPUT="$JENKINS_JMAP_OUTPUT_DIR/jenkinsmemory.output.tar.gz"
fi

cd $JENKINS_JMAP_OUTPUT_DIR

# Output the Default Settings to the end user
echo $(date) "Histo output will be generated"

# Generate jmap output
write_histo $pid "jmapHistoOutput.$pid.txt"

echo $(date) "jmapHistoOutput.$pid.txt file generated."

if [ $mode -eq 1 ]
then
  # Output the Default Settings to the end user
  echo $(date) "jmap headdump file will be generated"
  # Generate a heap dump
  write_dump $pid "heapdump.$pid.hprof"
  echo $(date) "heapdump.$pid.hprof file generated."
fi # End heap dump mode

# Brief pause to make sure all data is collected.
echo $(date) "Packaging data and preparing for cleanup."
sleep 10

if [ $mode -eq 0 ]
then
  tar_file "$CBSUPPORT_OUTPUT" jmapHistoOutput.$pid.txt

  # Clean up the jmapHistoOutput.$pid.txt files
  rm jmapHistoOutput.$pid.txt

  # Notify end user
  echo $(date) "The jmapHistoOutput.$pid.txt file has been removed."
  echo $(date) "The $script_name.sh script is complete."
  echo
  echo $(date) "Please upload the $script_name.output.tar.gz file to your ticket for review."
  exit 0
elif [ $mode -eq 1 ]
then
  tar_file "$CBSUPPORT_OUTPUT" jmapHistoOutput.$pid.txt heapdump.$pid.hprof

  # Clean up the jmapHistoOutput.$pid.txt, and heap dump files
  rm jmapHistoOutput.$pid.txt
  rm heapdump.$pid.hprof

  # Notify end user
  echo $(date) "The jmapHistoOutput.$pid.txt, heapdump.$pid.hprof files have been removed."
  echo $(date) "The $script_name.sh script is complete."
  echo
  echo $(date) "Please upload the $script_name.output.tar.gz file to your ticket for review."
fi

exit 0
