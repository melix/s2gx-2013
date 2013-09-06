package com.acme.cz

import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.MultipleCompilationErrorsException
import org.codehaus.groovy.control.customizers.SecureASTCustomizer

class SecureASTCustomizerTest extends GroovyTestCase {
    void testMethodCallIsAllowedWithoutCustomizer() {

        def shell = new GroovyShell()
        def result = shell.evaluate '''
            Math.cos(Math.PI)
        '''
        assert result == -1
    }

    void testMethodCallIsBlockedThanksToCustomizer() {
        def config = new CompilerConfiguration()
        def cz = new SecureASTCustomizer()
        cz.expressionsBlacklist = [MethodCallExpression,StaticMethodCallExpression]
        config.addCompilationCustomizers(cz)
        def shell = new GroovyShell(config)
        shouldFail(MultipleCompilationErrorsException) {
            shell.evaluate 'Math.cos(Math.PI)'
        }
    }
}
