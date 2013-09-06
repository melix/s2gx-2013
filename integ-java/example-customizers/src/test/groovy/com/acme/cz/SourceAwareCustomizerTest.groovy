package com.acme.cz

import groovy.transform.ToString
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ASTTransformationCustomizer
import org.codehaus.groovy.control.customizers.SourceAwareCustomizer

class SourceAwareCustomizerTest extends GroovyTestCase {
    public void testThatToStringIsAppliedIfFilenameWithBean() {
        def config = new CompilerConfiguration()
        def astCZ = new ASTTransformationCustomizer(includeNames:true, ToString)
        def saCZ = new SourceAwareCustomizer(astCZ)
        saCZ.baseNameValidator = { it.endsWith 'Bean' }
        config.addCompilationCustomizers(saCZ)
        def shell = new GroovyShell(config)
        def result = shell.evaluate('''
            class Person {
                String firstName
                String lastName
            }

            new Person(firstName:'John', lastName: 'Doe')
        ''', 'PersonTest.groovy');
        assert result.toString().startsWith('Person@')

        // with a different file name
        result = shell.evaluate('''
            class Person {
                String firstName
                String lastName
            }

            new Person(firstName:'John', lastName: 'Doe')
        ''', 'PersonBean.groovy');
        assert result.toString() == 'Person(firstName:John, lastName:Doe)'

    }
}
