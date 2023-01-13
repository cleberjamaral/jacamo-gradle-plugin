package org.jacamo.plugins.jacamo

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class TestJacamoTask extends DefaultTask {

    TestJacamoTask() {
    }

    @TaskAction
    def testJacamo() {
        description 'runs JaCaMo tests'
        def errorOnTests = false
        //outputs.upToDateWhen { false } // disable cache
        def tempOut = new ByteArrayOutputStream()
        def tempErr = new ByteArrayOutputStream()

        try {
            println 'testJacamo #1'
            println 'testJacamo #2'
            println 'testJacamo #3'
            javaexec {
                main = 'jacamo.infra.JaCaMoLauncher'
                if (gradle.startParameter.logLevel.toString().equals("DEBUG")) {
                    args = ['src/test/tests.jcm', '--log-conf', '$jasonJar/templates/console-debug-logging.properties']
                } else if (gradle.startParameter.logLevel.toString().equals("INFO")) {
                    args = ['src/test/tests.jcm', '--log-conf', '$jasonJar/templates/console-info-logging.properties']
                } else {
                    args = ['src/test/tests.jcm', '--log-conf', '$jasonJar/templates/console-lifecycle-logging.properties']
                }
                println 'testJacamo #4'
                classpath sourceSets.main.runtimeClasspath
                println 'testJacamo #5'
                standardOutput = new org.apache.tools.ant.util.TeeOutputStream(System.out, tempOut);
                errorOutput = new org.apache.tools.ant.util.TeeOutputStream(System.err, tempErr);
                println 'testJacamo #6'
                ext.stdout = {
                    return tempOut.toString()
                }
                ext.errout = {
                    return tempErr.toString()
                }
                println 'testJacamo #7'
            }
        } catch (Exception e) {
            println 'testJacamo #8'
            errorOnTests = true
        }

            println 'testJacamo #9'
            def styler = 'black red green yellow blue magenta cyan white'
                .split().toList().withIndex(30)
                .collectEntries { key, val -> [(key) : { "\033[${val}m${it}\033[0m" }] }
            println 'testJacamo #10'
            println 'testJacamo #11'
            tempOut.splitEachLine('\n') { String line ->
                line = line.replace("TESTING","${styler['yellow']('TESTING')}")
                line = line.replace("PASSED","${styler['green']('PASSED')}")
                line = line.replace("FAILED","${styler['red']('FAILED')}")
                line = line.replace("TODO","${styler['magenta']('TODO')}")
                line = line.replace("LAUNCHING","${styler['blue']('LAUNCHING')}")
                println line
            }
            println 'testJacamo #12'
            println 'testJacamo #13'
            tempErr.splitEachLine('\n') { String line ->
                line = line.replace("TESTING","${styler['yellow']('TESTING')}")
                line = line.replace("PASSED","${styler['green']('PASSED')}")
                line = line.replace("FAILED","${styler['red']('FAILED')}")
                line = line.replace("TODO","${styler['magenta']('TODO')}")
                line = line.replace("LAUNCHING","${styler['blue']('LAUNCHING')}")
                println line
            }
            println 'testJacamo #14'            
            if (errorOnTests) {
                println 'testJacamo #15'
                //throw new GradleException('JaCaMo tests: ERROR!')
            }

        //shouldRunAfter(tasks.named('test'))
        /*

> Task :test NO-SOURCE
Watching 12 directories to track changes
Skipping task ':test' as it has no source files and no previous output files.
:test (Thread[Execution worker for ':' Thread 2,5,main]) completed. Took 0.007 secs.
:testJacamo (Thread[Execution worker for ':' Thread 2,5,main]) started.

> Task :testJacamo FAILED
Caching disabled for task ':testJacamo' because:
  Build cache is disabled
Task ':testJacamo' is not up-to-date because:
  Task has not declared any outputs despite executing actions.
testJacamo #1
testJacamo #2
testJacamo #3
testJacamo #8
testJacamo #9
testJacamo #10
:testJacamo (Thread[Execution worker for ':' Thread 2,5,main]) completed. Took 0.414 secs.

FAILURE: Build failed with an exception.

* What went wrong:
E
        */
    }
}
