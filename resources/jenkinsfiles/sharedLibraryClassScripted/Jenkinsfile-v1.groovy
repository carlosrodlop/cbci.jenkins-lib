@Library(["my-test-lib@master"]) _

import lib.dummy.HelloWorld_v1

def g
g = new HelloWorld_v1()

node ("master") {
    g.greating ("Hola from Master")
}
node ("default-java") {
   g.greating ("Hola from K8 Agent")
}
