import org.junit.*
import com.lesfurets.jenkins.unit.*
import static groovy.test.GroovyAssert.*

class ToAlphanumericTest extends BasePipelineTest {
    def toAlphanumeric

    @Before
    void setUp() {
        super.setUp()
        // load toAlphanumeric
        toAlphanumeric = loadScript("vars/fToAlphanumeric.groovy")
    }

    @Test
    void testCall() {
        // call toAlphanumeric and check result
        def result = toAlphanumeric(text: "a_B-c.1")
        assertEquals "result:", "abc1", result
    }
}
