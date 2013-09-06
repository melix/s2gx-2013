package com.acme.cz

import groovy.transform.ToString
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.classgen.GeneratorContext
import org.codehaus.groovy.control.CompilationFailedException
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.customizers.ASTTransformationCustomizer
import org.codehaus.groovy.control.customizers.CompilationCustomizer
import org.codehaus.groovy.control.customizers.SourceAwareCustomizer

class UserCustomizerTest extends GroovyTestCase {
    public void testThatToStringIsAppliedIfFilenameWithBean() {
        def config = new CompilerConfiguration()
        def astCZ = new ASTTransformationCustomizer(includeNames:true, ToString)
        def saCZ = new CompilationCustomizer(CompilePhase.SEMANTIC_ANALYSIS) {
            @Override
            void call(final SourceUnit source, final GeneratorContext context, final ClassNode classNode) throws CompilationFailedException {
                if (classNode.name.endsWith('Bean')) {
                    astCZ.call(source, context, classNode)
                }
            }
        }
        config.addCompilationCustomizers(saCZ)
        def shell = new GroovyShell(config)
        def result = shell.evaluate '''
            class Person {
                String firstName
                String lastName
            }

            new Person(firstName:'John', lastName: 'Doe')
        '''
        assert result.toString().startsWith('Person@')

        // with a different file name
        result = shell.evaluate '''
            class PersonBean {
                String firstName
                String lastName
            }

            new PersonBean(firstName:'John', lastName: 'Doe')
        '''
        assert result.toString() == 'PersonBean(firstName:John, lastName:Doe)'

    }
}
