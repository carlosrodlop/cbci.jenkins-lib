// ====================================================================================
// Pre-requisites:
// 1) Create an Alert in CloudBees Monitoring - Memory above 90%
//   - Condition: vm.memory.heap.usage - 0.9
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
        MAIL_ADDRESS="mail.example@example.com"
   }
   agent {
        label "master"
   }
   stages {
        stage("Prepare") {
            steps {
                deleteDir()
                script {
                    timestamp = sh(script: "date +%d-%b-%Y_%H-%M-%S", returnStdout: true).trim()
                    def master_name_array = ManagementFactory.getRuntimeMXBean().getName().split("@");
                    masterPid = master_name_array[0]
                }
            }
        }
        stage("Collect data") {
            stages {
                stage("VM Description"){
                    steps{
                        dir ("memory/VM_Description"){
                            sh "echo 'VM.version \n ==========' > VM_description.txt"
                            sh "jcmd $masterPid VM.version >> VM_description.txt"
                            sh "echo 'VM.system_properties \n ==========' >> VM_description.txt"
                            sh "jcmd $masterPid VM.system_properties >> VM_description.txt"
                            sh "echo 'VM.flags \n ==========' >> VM_description.txt"
                            sh "jcmd $masterPid VM.flags >> VM_description.txt"
                            }
                        }
                    }
                stage("VM Heap Dump"){
                    steps{
                        dir ("memory/VM_HeapDump"){
                            // Option 1 (recommended)
                            // sh """
                            // curl https://s3.amazonaws.com/cloudbees-jenkins-scripts/e206a5-linux/jenkinsjmap.sh > jenkinsjmap.sh
                            // chmod +x jenkinsjmap.sh
                            // ./jenkinsjmap.sh $masterPid 1
                            // """
                            // Option 2
                            // Heap Dump
                            sh "jmap -dump:format=b,file=heap_dump.hprof $masterPid"
                            // Class Histogram (Classes taking the most memory are listed at the top, and classes are listed in a descending order)
                            sh "jcmd $masterPid GC.class_histogram > class_histogram.txt"
                        }
                    }
                }
            }
        }
    }
    post {
        success {
            zip zipFile: "Memory_Data-${timestamp}.zip", archive: true, dir: "memory"
            mail body: "<b>Master Memory Above 90%</b> <br>Reported by: ${env.JOB_NAME} <br>System time: ${timestamp} <br>Build Number: ${env.BUILD_NUMBER} <br>URL de build: ${env.BUILD_URL} <br>Data Package Success: Memory_Data-${timestamp}.zip",
                 charset: 'UTF-8', from: '', mimeType: 'text/html', subject: "${env.JOB_BASE_NAME} SUCCESS", to: "${MAIL_ADDRESS}";
        }
        failure {
             mail body: "<b>Master Memory Above 90%</b> <br>Reported by: ${env.JOB_NAME} <br>System time: ${timestamp} <br>Build Number: ${env.BUILD_NUMBER} <br>URL de build: ${env.BUILD_URL} <br>Data Package Failed :(",
                charset: 'UTF-8', from: '', mimeType: 'text/html', subject: "${env.JOB_BASE_NAME} SUCCESS", to: "${MAIL_ADDRESS}";
        }
    }
}
