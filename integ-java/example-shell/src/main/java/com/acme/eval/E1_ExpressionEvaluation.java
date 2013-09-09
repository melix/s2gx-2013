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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class E1_ExpressionEvaluation {
    public static void main(String[] args) throws IOException {
        Eval.me("println 'This is an expression evaluator'");
        Object result = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (result == null || !result.equals(-1)) {
            System.out.println("What do you want to evaluate?");
            String code = reader.readLine();
            result = Eval.me(code);
            System.out.println("result = " + result);
        }
    }
}
