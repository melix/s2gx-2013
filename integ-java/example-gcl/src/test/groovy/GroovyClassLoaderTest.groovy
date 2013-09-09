class GroovyClassLoaderTest extends GroovyTestCase {
    void testShouldParseClass() {
        GroovyClassLoader loader = new GroovyClassLoader(this.class.classLoader)
        def clazz = loader.parseClass '''
            class GroovyPersonFactory implements PersonFactory {

                @Override
                Person create(final String firstName, final String lastName) {
                    new Person(firstName, lastName)
                }
            }
        '''
        assert PersonFactory.isAssignableFrom(clazz)
        def factory = clazz.newInstance()
        def p1 = new Person(firstName: 'John', lastName: 'Doe')
        def p2 = factory.create('John', 'Doe')
        assert p1 == p2
    }
}
