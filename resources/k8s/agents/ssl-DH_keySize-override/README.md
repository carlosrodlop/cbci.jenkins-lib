
## Requirement

```sh
kubectl create configmap override-java-security --from-file java.config
```

## Pipeline

```groovy
// Loading Shared Libraries
library identifier: 'my-shared-libraries@demo', retriever: modernSCM(
  [$class: 'GitSCMSource',
   remote: 'https://github.com/carlosrodlop/my-jenkins-demos.git'])

pipeline {
  agent {
    kubernetes {
       defaultContainer "maven"
       yaml libraryResource("k8s/agents/ssl-DH_keySize-override/ssl_pod.yaml")
      }
  }
  stages {
    stage("Hello World") {
      steps {
        sh "mvn --version"
      }
    }
  }
}
```
