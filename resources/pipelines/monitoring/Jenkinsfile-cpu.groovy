/* groovylint-disable CompileStatic, LineLength, NestedBlockDepth, NoDef, UnnecessaryGetter */
// ====================================================================================
// Pre-requisites:
// 1) Create an Alert in CloudBees Monitoring - CPU above 90%
//   - Condition: vm.cpu.load - 0.9
//   - Action: Trigger build of a remote Job: link to this job
// 2) Assign one executor to the master node (just for troubleshooting)
// 3) The following items need to be approved in Script Security
//   - java.lang.management.ManagementFactory
//   - ManagementFactory.getRuntimeMXBean().getName()
// Tested on:
//   - Core on Modern: CloudBees Core Managed Master 2.222.1.1
// References:
// - https://docs.oracle.com/javase/8/docs/technotes/guides/troubleshoot/memleaks004.html
// - https://dzone.com/articles/jcmd-one-jdk-command-line-tool-to-rule-them-all
// =====================================================================================
import java.lang.management.ManagementFactory

def timestamp
def masterPid
pipeline {
    environment {
        MAIL_ADDRESS = 'mail.example@example.com'
    }
    agent {
        label 'master'
    }
    stages {
        stage('Prepare') {
            steps {
                deleteDir()
                script {
                    timestamp = sh(script: 'date +%d-%b-%Y_%H-%M-%S', returnStdout: true).trim()
                    master_name_array = ManagementFactory.getRuntimeMXBean().getName().split('@')
                    masterPid = master_name_array[0]
                }
            }
        }
        stage('Collect data') {
            stages {
                stage('VM Description') {
                    steps {
                        dir('cpu/VM_Description') {
                            sh "echo 'VM.version \n ==========' > VM_description.txt"
                            sh "jcmd $masterPid VM.version >> VM_description.txt"
                            sh "echo 'VM.system_properties \n ==========' >> VM_description.txt"
                            sh "jcmd $masterPid VM.system_properties >> VM_description.txt"
                            sh "echo 'VM.flags \n ==========' >> VM_description.txt"
                            sh "jcmd $masterPid VM.flags >> VM_description.txt"
                        }
                    }
                }
                stage('VM Threads') {
                    environment {
                        // For option 2
                        FREQUENCY = 100
                        RUNS = 2
                    }
                    steps {
                        dir('cpu/VM_Threads') {
                            // Option 1 (recommended)
                            sh '''
                            curl https://s3.amazonaws.com/cloudbees-jenkins-scripts/e206a5-linux/jenkinshangWithJstack.sh > jenkinshangWithJstack.sh
                            chmod +x jenkinshangWithJstack.sh
                            ./jenkinshangWithJstack.sh $masterPid
                            '''
                        // Option 2
                        // script {
                        //     for (int i = 0; i < "$RUNS".toInteger() ; i++) {
                        //         sh(script: "jcmd $masterPid Thread.print > jcmd-Thread${i}.txt", returnStdout: false)
                        //         // sh(script: "jstack -l $masterPid > jstack${i}.txt", returnStdout: false)
                        //         sleep "$FREQUENCY"
                        //     }
                        // }
                        }
                    }
                }
            }
        }
    }
    post {
        success {
            zip zipFile: "CPU_Data-${timestamp}.zip", archive: true, dir: 'cpu'
        }
    }
}
