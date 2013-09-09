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
import groovy.lang.Script;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class E4_GroovyShellParseTest {

    @Test
    public void shouldInstantiateScript() {
        Binding binding = new Binding();
        GroovyShell shell = new GroovyShell(binding);
        Script myScript = shell.parse("x+y");
        binding.setVariable("x", 2);
        binding.setVariable("y", 3);

        Object result = myScript.run();
        assertEquals(5, result);

        binding.setVariable("x", 4);
        binding.setVariable("y", 5);

        // reusing the *same instance* of script
        result = myScript.run();
        assertEquals(9, result);
    }

    @Test
    public void shouldUseScriptBaseClass() {
        CompilerConfiguration config = new CompilerConfiguration();
        config.setScriptBaseClass("com.acme.shell.E4_GroovyShellParseTest.MyScript");

        GroovyShell shell = new GroovyShell(config);
        MyScript myScript = (MyScript) shell.parse("x+y");
        myScript.setXY(2,3);

        Object result = myScript.run();
        assertEquals(5, result);

        myScript.setXY(4,5);

        // reusing the *same instance* of script
        result = myScript.run();
        assertEquals(9, result);

    }

    public abstract static class MyScript extends Script {
        private int x;
        private int y;

        public void setXY(final int x, final int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
