#!/bin/bash
####################################################################################
# This script is used to collect data for
# 'RequiredData: Performance, Hang or High CPU Issues on Jenkins running on Linux'
#
SCRIPT_VERSION=v3.5.2019.3.1
#####################################################################################

print_help ()
{
    cat <<EOM
Unable to find required PID argument.  Please rerun the script as follows:
$(basename $0) PID [duration] [frequency]

    PID:       Jenkins PID
    duration:  Tests duration time in seconds (default 60 seconds)
    frequency: Number of seconds that will wait until next data require (default 5 seconds)

    Optional environment vars

      JAVA_HOME               used to locate Java Troubleshooting Tools
      JENKINS_USERID          jenkins userid if this script is run as root instead of jenkins userid
      JENKINS_HANG_OUTPUT_DIR output dir

    Run $(basename $0) --help to print help.
EOM
}

script_validation ()
{
  echo "Script Validation Results"
  echo "Moving to ${JENKINS_HANG_OUTPUT_DIR}"

  HERE="$(pwd)"
  cd $JENKINS_HANG_OUTPUT_DIR

  #check if the directory can be written to by the user that is running the script, i.e. jenkins user
  touch testFile.txt 2>/dev/null

  if [ -e testFile.txt ]
  then
    echo "[SUCCESS]: This directory can be written to by the script"
  fi

  if [ ! -e testFile.txt ]
  then
   echo "[ERROR]: This directory cannot be written to by the script. Please either run this script from a directory that can be written to or use the optional environment variable: JENKINS_HANG_OUTPUT_DIR ."
   exit 1
  fi

  rm -rf testFile.txt

  #run command -v foo to check that the required commands are installed
  command -v top >/dev/null 2>&1 || { echo && echo >&2 "[WARN]: top is recommended but it's not installed." && echo;}

  command -v vmstat >/dev/null 2>&1 || { echo && echo >&2 "[WARN]: vmstat is recommended but it's not installed." && echo;}

  command -v netstat >/dev/null 2>&1 || { echo && echo >&2 "[WARN]: netstat is recommended but it's not installed." && echo;}

  command -v iostat >/dev/null 2>&1 || { echo && echo >&2 "[WARN]: iostat is recommended but it's not installed." && echo;}

  local jcmd_bin="jcmd"
  local jstack_bin="jstack"

  if [ -n "${JAVA_HOME}" ]; then
    echo "[INFO]: JAVA_HOME is set. Looking for Java Troubleshooting Tools \"${JAVA_HOME}/bin\"."
    jcmd_bin="${JAVA_HOME}/bin/jcmd"
    jstack_bin="${JAVA_HOME}/bin/jstack"
  else
    echo "[INFO]: JAVA_HOME is NOT set. Looking for Java Troubleshooting Tools on the PATH."
  fi
  if ! command -v ${jcmd_bin} >/dev/null 2>&1 && ! command -v ${jstack_bin} >/dev/null 2>&1; then
    echo && echo >&2 "[ERROR]: jcmd or jstack is required but neither was found. Either the full Java JDK is not installed or it is not the path of the user that is running Jenkins." && echo;
    exit 1
  fi

  echo "Moving back to current dir $HERE"
  cd $HERE
}


#####################################
#pid
duration=60
frequency=5
#####################################

if [ $# -eq 1 ]
then
  if [ "$1" = "--help" ]; then
    print_help
    exit 0
  fi

  pid=$1
elif [ $# -eq 2 ]
then
  pid=$1
  duration=$2
elif [ $# -eq 3 ]
then
  pid=$1
  duration=$2
  frequency=$3
else
  print_help $0
  exit 1
fi

if [ -z "$JENKINS_HANG_OUTPUT_DIR" ]; then
    JENKINS_HANG_OUTPUT_DIR="$(pwd)"
    echo $(date) "Output dir $JENKINS_HANG_OUTPUT_DIR"
fi

# Validate
script_validation $0

declare jcmd_bin="jcmd"
declare jstack_bin="jstack"
if [ -n "$JAVA_HOME" ]; then
  jcmd_bin="${JAVA_HOME}/bin/jcmd"
  jstack_bin="$JAVA_HOME/bin/jstack"
fi

declare cmd_prefix=""
if [ -n "$JENKINS_USERID" ]; then
    cmd_prefix="sudo -u $JENKINS_USERID"
    echo $(date) "Jenkins user $JENKINS_USERID"
fi

write_threads ()
{
  if command -v ${jcmd_bin} >/dev/null 2>&1; then
    ${cmd_prefix} ${jcmd_bin} $1 Thread.print -l > $2
  elif command -v ${jstack_bin} >/dev/null 2>&1; then
    ${cmd_prefix} ${jstack_bin} -l $1 > $2
  fi
}


# Create temporary directories
TEMP_DIR="$JENKINS_HANG_OUTPUT_DIR/tmp.$pid.$(date +%Y%m%d%H%M%S)"
echo $(date) "Temporary dir $TEMP_DIR"
mkdir -p $TEMP_DIR
mkdir "$TEMP_DIR"/iostat "$TEMP_DIR"/threads "$TEMP_DIR"/netstat "$TEMP_DIR"/topdashHOutput "$TEMP_DIR"/topOutput "$TEMP_DIR"/vmstat "$TEMP_DIR"/nfsiostat "$TEMP_DIR"/nfsstat

# Begin script and notify the end user
echo $(date) "The jenkinshangWithJstack.sh script $SCRIPT_VERSION is starting in custom mode." && echo $(date) "The jenkinshangWithJstack.sh script $SCRIPT_VERSION is starting in custom mode." >> "$TEMP_DIR"/mode.txt
echo $(date) "The pid is $pid" >> "$TEMP_DIR"/mode.txt
echo $(date) "The custom duration is $duration" >> "$TEMP_DIR"/mode.txt
echo $(date) "The custom thread dump generation frequency is $frequency" >> "$TEMP_DIR"/mode.txt


# Output the Default Settings to the end user
echo $(date) "The custom mode should only be used if requested && if data should be collected for longer than 1 minute"
echo $(date) "The jenkinshangWithJstack.sh script will run for $duration seconds."
echo $(date) "It will generate a full data generation (threadDump, iostat, vmstat, netstat, top) every $frequency seconds."
echo $(date) ">>>>>>>>>>>>>>>The frequency Has To Divide into the duration by a whole integer.<<<<<<<<<<<<<<<"
echo $(date) ">>>>>>>>>>>>>>>The duration Divided by 60 should also be a whole integer.<<<<<<<<<<<<<<<"
echo $(date) ">>>>>>>>>>>>>>>The duration Divided by 5 should also be a whole integer.<<<<<<<<<<<<<<<"
echo $(date) ">>>>>>>>>>>>>>>Setting the frequency to low, i.e. 1 second, may cause the data to be inconclusive.<<<<<<<<<<<<<<<"

# Begin data generation once every $frequency seconds.
while [ $duration -gt 0 ]
do
  # Taking top data collection
  echo $(date) "Taking top data collection."
  COLUMNS=300 top -bc -n 1 > "$TEMP_DIR"/topOutput/topOutput.$(date +%Y%m%d%H%M%S).txt &

  # Taking topdashH data collection
  echo $(date) "Taking TopdashH data collection."
  top -bH -p $pid -n 1 > "$TEMP_DIR"/topdashHOutput/topdashHOutput.$pid.$(date +%Y%m%d%H%M%S).txt &

  # Taking vmstat data collection in the background
  echo $(date) "Taking vmstat data collection."
  vmstat > "$TEMP_DIR"/vmstat/vmstat.$(date +%Y%m%d%H%M%S).out &

  # Taking netstat data
  echo $(date) "Taking netstat collection."
  netstat -pan > "$TEMP_DIR"/netstat/netstat.$(date +%Y%m%d%H%M%S).out &

  # Taking iostat data collection
  echo $(date) "Taking iostat data collection."
  if which iostat 2>/dev/null>/dev/null; then
        iostat -t > "$TEMP_DIR"/iostat/iostat.$(date +%Y%m%d%H%M%S).out &
      else
        echo $(date) "SEVERE: The command iostat was not found"
  fi

  # Taking nfsiostat data collection
  echo $(date) "Taking nfsiostat data collection."
  if which nfsiostat 2>/dev/null>/dev/null; then
        nfsiostat > "$TEMP_DIR"/nfsiostat/nfsiostat.$(date +%Y%m%d%H%M%S).out &
      else
        echo $(date) "SEVERE: The command nfsiostat was not found"
  fi

  # Taking nfsstat data collection
  echo $(date) "Taking nfsstat data collection."
  if which nfsstat 2>/dev/null>/dev/null; then
        nfsstat -c > "$TEMP_DIR"/nfsstat/nfsstat.$(date +%Y%m%d%H%M%S).out &
      else
        echo $(date) "SEVERE: The command nfsstat was not found"
  fi

  # Taking a threadDump
  THREADS_FILENAME="$TEMP_DIR"/threads/threads.$pid.$(date +%Y%m%d%H%M%S).txt
  write_threads $pid "$THREADS_FILENAME" &
  # Record the process PID
  THREAD_DUMP_PID=$!
  echo $(date) "Collected a threadDump for PID $pid."

  # Wait for the thread dump background process
  wait $THREAD_DUMP_PID
  # Get the exit code of the $THREAD_DUMP_PID
  THREAD_DUMP_PID_STATUS=$?
  # Wait for all background process
  wait

  if [ $THREAD_DUMP_PID_STATUS -ne 0 ]; then
    rm -r "$TEMP_DIR"
    echo "<<<<<<<<<<<<<<< ERROR: The script failed to collect a thread dump. Maybe it is not launched with the same user that jenkins is running as. Try with sudo -u <JENKINS_USERID> >>>>>>>>>>>>>>>"
    exit 1
  fi

  # Pause for THREADDUMP_FREQUENCY seconds.
  echo $(date) "A new collection will start in $frequency seconds."

  sleep $frequency

  # Update duration
  duration=`expr $duration - $frequency`
done

# Brief pause to make sure all data is collected.
echo $(date) "Packaging data and preparing for cleanup."

HERE="$(pwd)"

echo $(date) "Moving to $JENKINS_HANG_OUTPUT_DIR"
cd "$TEMP_DIR"
JENKINS_HANG_ARCHIVE_NAME="${CBSUPPORT_OUTPUT:-jenkinshangWithJstack.$pid.output.tar.gz}"
tar -czf "${JENKINS_HANG_ARCHIVE_NAME}" topOutput topdashHOutput mode.txt threads vmstat netstat iostat nfsiostat nfsstat
cp "${JENKINS_HANG_ARCHIVE_NAME}" ..

echo $(date) "Cleanup files"
# Clean up the topOutput.txt and topdashHOutput.$pid.txt files
rm -r "$TEMP_DIR"

echo $(date) "Moving back to current dir $HERE"
cd $HERE

# Notify end user. Do not do it when running in the context of cbsupport as the message is misleading for the end user.
if [ -z "$CBSUPPORT_OUTPUT" ] ; then
  echo $(date) "The temporary dir \"${TEMP_DIR}\" has been deleted"
  echo $(date) "The jenkinshangWithJstack.sh script in CUSTOM MODE is complete."
  echo
  echo $(date) "The Output files are contained within !>>>!   ${JENKINS_HANG_ARCHIVE_NAME}   !<<<!"
  echo $(date) "Please upload the ${JENKINS_HANG_ARCHIVE_NAME} archive to your ticket for review."
fi
exit 0
