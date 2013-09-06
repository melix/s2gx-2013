/*
 * Copyright 2003-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.acme.shell;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.MissingPropertyException;
import org.codehaus.groovy.runtime.MethodClosure;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class GroovyShellEvaluateTest {
    @Test
    public void testSimpleShellEvaluation() {
        GroovyShell shell = new GroovyShell();
        Object result = shell.evaluate("1+1");
        assertEquals(2, result);
    }

    @Test
    public void testBindingExport() {
        Binding binding = new Binding();
        GroovyShell shell = new GroovyShell(binding);
        shell.evaluate("x=123");
        Object val = binding.getVariable("x");
        assertEquals(123, val);
    }

    @Test
    public void testBindingOverride() {
        Binding binding = new Binding();
        binding.setVariable("x", 123);
        GroovyShell shell = new GroovyShell(binding);
        shell.evaluate("x=456");
        Object val = binding.getVariable("x");
        assertEquals(456, val);
    }

    @Test(expected=MissingPropertyException.class)
    public void testBindingVsLocalVariable() {
        Binding binding = new Binding();
        GroovyShell shell = new GroovyShell(binding);
        shell.evaluate("def x=456");
        Object val = binding.getVariable("x");
    }

    @Test
    public void testNotCaching() {
        String script = "class Foo {}; Foo.class";
        GroovyShell shell = new GroovyShell();

        // store result of the script, which is a class
        // into a local variable (be careful, memory leak!)
        Class c1 = (Class) shell.evaluate(script);
        assertEquals("Foo", c1.getSimpleName());

        // with the same script, evaluate again
        Class c2 = (Class) shell.evaluate(script);
        assertEquals("Foo", c2.getSimpleName());

        // what do you expect?
        assertNotEquals(c1,c2);

    }

    @Test
    public void testMethodClosure() {
        Helper helper = new Helper();
        Binding binding = new Binding();
        binding.setVariable("hello", new MethodClosure(helper, "sayHello"));
        GroovyShell shell = new GroovyShell(binding);
        shell.evaluate("assert hello() == true");
    }

    public static class Helper {
        boolean sayHello() {
            System.out.println("Say hello from Java!");
            return true;
        }
    }
}
