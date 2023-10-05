//https://github.com/elandsness/stresscpubash/tree/master
def call(configYaml) {

    Map config = readYaml text: "${configYaml}"

    pipeline {
        environment {
            N_CYCLES = "${config.n_cycles}"
            N_PROCESS = "${config.n_process}"
            SECS = "${config.secs}"
        }
        agent {
            label 'built-in'
        }
        triggers {
            eventTrigger jmespathQuery("eventName=='${config.eventName}'")
        }
        stages {
            stage('Stress CPU') {
                steps {
                    writeFile(file: 'stresscpu.sh', text: '''
numProcs=$(nproc 2>&1)
numProcs=$((numProcs+1))
if [ ! -z $1 ]; then
   numProcs=$1
fi

duration=60
if [ ! -z $2 ]; then
   duration=$2
fi

#setup bashtrap
trap bashtrap INT

#function to end cpu stress cycles
function cleanup {
   echo "Cleaning up..."
   killall -9 dd
}

#cleanup if canceled early
bashtrap(){
   echo " CPU stress cycle canceled."
}

if [ $1 = '-h' ]; then
   echo 'Usage ./stresscpu.sh <number processes> <duration in seconds>'
else
   for i in `seq 1 $numProcs`
   do
      echo "Spawning process $i"
      dd if=/dev/urandom >> /dev/null &
   done

   echo "Running for $duration seconds"
   sleep $duration
   cleanup
   echo "Done!"
fi
''')
                sh "for i in {1..${N_CYCLES}}; do bash stresscpu.sh ${N_PROCESS} ${SECS}; done"         
                }
            }
        }
    }
}   
