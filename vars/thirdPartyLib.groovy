// it needs to be maven type
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
