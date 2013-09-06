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
package com.acme.eval;

import groovy.util.Eval;

public class ExpressionEvaluationWithBinding {
    public static void main(String[] args) {

        // capture the "x" variable
        Object result = Eval.me("x", 2, "2*x+1");
        System.out.println("result = " + result);

        // short version
        result = Eval.x(2, "3*x+2");
        System.out.println("result = " + result);

        // two-param binding
        result = Eval.xy(3, 4, "x*y+3");
        System.out.println("result = " + result);

        // three-param binding
        result = Eval.xyz(3, 4, 5, "x*y+z");
        System.out.println("result = " + result);

    }
}
