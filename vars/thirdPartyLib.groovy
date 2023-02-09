// it needs to be maven type
// @GrabResolver(name='artifactory', root='http://artifactory-oss.dse-beescloud.com:80/artifactory/my-repo-libs-release-local/', m2Compatible=true)
@Grab('org.apache.commons:commons-math3:3.4.1')
import org.apache.commons.math3.primes.Primes

//https://www.jenkins.io/doc/book/pipeline/shared-libraries/#using-third-party-libraries
def isPrime(int count) {
  if (!Primes.isPrime(count)) {
    error "${count} was not prime"
  } else {
    echo "${count} is a prime"
  }
}
