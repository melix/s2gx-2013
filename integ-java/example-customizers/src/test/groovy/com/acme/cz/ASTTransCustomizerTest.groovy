package com.acme.cz

import groovy.transform.TimedInterrupt
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ASTTransformationCustomizer

import java.util.concurrent.TimeoutException

class ASTTransCustomizerTest extends GroovyTestCase {
    void testShouldRequireExplicitASTXForm() {
        def shell = new GroovyShell()
        try {
            shell.evaluate '''import groovy.transform.TimedInterrupt

                @TimedInterrupt(value=1L)
                class DSL {
                    void infiniteLoop() {
                        while (true) {
                            1+1
                        }
                    }
                }
                new DSL().infiniteLoop()
            '''
        } catch (TimeoutException ex) {
            // all is fine
        }
    }

    void testNicerDSLWithoutExplicitTimedInterrupt() {
        def config = new CompilerConfiguration()
        config.addCompilationCustomizers(
                new ASTTransformationCustomizer(value:1L, TimedInterrupt)
        )
        def shell = new GroovyShell(config)
        try {
            shell.evaluate '''
               while (true) {
                  1+1
               }
            '''
        } catch (TimeoutException ex) {
            // all is fine
        }
    }

}
