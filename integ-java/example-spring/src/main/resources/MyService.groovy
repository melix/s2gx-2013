import com.acme.spring.Greeter
import groovy.transform.CompileStatic

@CompileStatic
class MyService implements Greeter {
    void sayHello() {
        println "Hello!"
    }
}
