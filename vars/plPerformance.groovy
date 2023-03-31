/* groovylint-disable DuplicateStringLiteral, NestedBlockDepth */

import java.lang.management.ManagementFactory

def call(mode) {

    def timestamp
    def masterPid
    def data_script

    pipeline {
        agent {
            label 'master'
        }
        options { // Increase Rotation with integration with External Storage (e.g Google Cloud Storage, S3)
            buildDiscarder(logRotator(numToKeepStr: '1', artifactNumToKeepStr: '1'))
        }
        environment {
            MODE = "${mode}"
        }
        stages {
            stage('Prepare') {
                steps {
                    deleteDir()
                    script {
                        timestamp = sh(script: 'date +%d-%b-%Y_%H-%M-%S', returnStdout: true).trim()
                        master_name_array = ManagementFactory.getRuntimeMXBean().getName().split('@');
                        masterPid = master_name_array[0]
                    }
                }
            }
            stage('Data Capture') {
                stages {
                    stage('JVM General') {
                        // Ref: https://docs.oracle.com/javase/8/docs/technotes/guides/troubleshoot/tooldescr006.html
                        environment {
                            OUTPUT = 'VM_description.txt'
                        }
                        steps {
                            dir('performance') {
                                sh """
                                    echo '==========\nVM.version\n==========\n\n' >> $OUTPUT
                                    jcmd $masterPid VM.version >> $OUTPUT
                                    echo '==========\nVM.system_properties\n==========\n\n' >> $OUTPUT
                                    jcmd $masterPid VM.system_properties >> $OUTPUT
                                    echo '==========\nVM.flags\n==========\n\n' >> $OUTPUT
                                    jcmd $masterPid VM.flags >> $OUTPUT
                                """
                                }
                            }
                        }
                    stage('JVM Performance') {
                        stages {
                            stage('CPU') {
                                // Ref: https://support.cloudbees.com/hc/en-us/articles/229370407
                                when {
                                    environment name: 'MODE', value: '1'
                                }
                                steps {
                                    dir('performance/cpu') {
                                        script {
                                            data_script = libraryResource 'bash/jenkinshangWithJstack.sh'
                                        }
                                        writeFile file: 'cpu_collector.sh', text: data_script
                                        sh """
                                        chmod +x cpu_collector.sh
                                        bash cpu_collector.sh $masterPid
                                        """
                                    }
                                }
                            }
                            stage('Memory') {
                                // Ref: https://support.cloudbees.com/hc/en-us/articles/115001122568
                                when {
                                    environment name: 'MODE', value: '2'
                                }
                                steps {
                                    dir('performance/memory') {
                                        script {
                                            data_script = libraryResource 'bash/jenkinsmemory.sh'
                                        }
                                        writeFile file: 'memory_collector.sh', text: data_script
                                        sh """
                                        export JENKINS_JMAP_OUTPUT_DIR=\$(pwd)
                                        chmod +x memory_collector.sh
                                        bash memory_collector.sh $masterPid 1
                                        """
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        post {
            // TODO: Include notication https://www.jenkins.io/doc/pipeline/tour/post/
            success {
                zip zipFile: "Performance_mode-${mode}_${timestamp}.zip", archive: true, dir: 'performance'
            }
        }
    }
}
