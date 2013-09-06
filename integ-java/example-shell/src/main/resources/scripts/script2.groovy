package scripts

import com.acme.shell.Greeter
import groovy.transform.ToString
import groovy.transform.TupleConstructor

@ToString(includeNames = true)
@TupleConstructor
class Person implements Greeter {
    String firstName
    String lastName
    int age

    public void greet() {
        println "Hello, I'm $firstName $lastName"
    }

    public void greet(String person) {
        println "Hi, $person, I'm $firstName $lastName"
    }

    public void greet(String person, String salutation) {
        println "$salutation, $person, I'm $firstName $lastName"
    }
}

new Person('Cédric', 'Champeau', 34)