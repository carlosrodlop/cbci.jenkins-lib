# Pipelines

## Gotchas

* Variable and parameters require to have double quotes within a Groovy. However, in Bash shell can use single or double.

```groovy
node {
    withCredentials([usernamePassword(credentialsId: "${CREDENTIAL_PASS}", passwordVariable: 'password', usernameVariable: 'user')]) {
       echo 'user: ${env.user}'
       echo 'password: ${env.password}'
  }
}
```
