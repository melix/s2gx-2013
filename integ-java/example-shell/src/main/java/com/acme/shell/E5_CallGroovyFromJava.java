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

import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class E5_CallGroovyFromJava {
    public static void main(String[] args) throws UnsupportedEncodingException {
        GroovyShell shell = new GroovyShell();
        Object person = shell.evaluate(
                new InputStreamReader(E5_CallGroovyFromJava.class.getClassLoader().getResourceAsStream("scripts/script1.groovy"), "UTF-8"));
        System.out.println("person = " + person);

        // cannot cast to Person because class has been dynamically generated
        // how can you call methods on it?
        // GroovyObject interface!
        GroovyObject goPerson = (GroovyObject) person;

        // get a property
        String firstName = (String) goPerson.getProperty("firstName");
        System.out.println("firstName = " + firstName);

        // call a method
        goPerson.invokeMethod("greet", null);
        goPerson.invokeMethod("greet", "SpringOne2GX");
        goPerson.invokeMethod("greet", new Object[]{"SpringOne2GX", "Hey"});
    }
}
