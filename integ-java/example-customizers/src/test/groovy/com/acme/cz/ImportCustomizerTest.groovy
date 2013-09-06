package com.acme.cz

import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ImportCustomizer
import org.codehaus.groovy.control.customizers.builder.CompilerCustomizationBuilder

class ImportCustomizerTest extends GroovyTestCase {
    void testRequiresAnImport() {
        def shell = new GroovyShell()
        def result = shell.evaluate '''import com.acme.cz.Notebook

            def book = new Notebook()
            book.addNote 'Note 1'
            book.addNote 'Note 2'

            book.notes
        '''
        assert result.size() == 2
    }

    void testScriptDoesNotRequireAnImport() {
        def config = new CompilerConfiguration()
        def icz = new ImportCustomizer()
        icz.addImports('com.acme.cz.Notebook')
        config.addCompilationCustomizers(icz)
        def shell = new GroovyShell(config)
        def result = shell.evaluate '''
            def book = new Notebook()
            book.addNote 'Note 1'
            book.addNote 'Note 2'

            book.notes
        '''
        assert result.size() == 2
    }

    void testScriptDoesNotRequireAnImportUsingBuilder() {
        def config = new CompilerConfiguration()
        CompilerCustomizationBuilder.withConfig(config) {
            imports 'com.acme.cz.Notebook'
        }
        def shell = new GroovyShell(config)
        def result = shell.evaluate '''
            def book = new Notebook()
            book.addNote 'Note 1'
            book.addNote 'Note 2'

            book.notes
        '''
        assert result.size() == 2
    }
}
