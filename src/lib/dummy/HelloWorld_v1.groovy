// Ref:https://www.jenkins.io/doc/book/pipeline/shared-libraries/#accessing-steps
package lib.dummy

def msg
def greating (input) {
    msg = "${input}"
    stage("Hello") {
        println("${msg}")
        sh ('echo ...')
    }
}

return this
