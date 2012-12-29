package com.joejernst.http;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;

/*
 Copyright 2012 Joe J. Ernst

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

public class TestHttp {

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void testGet() throws IOException {

        Response httpResponse = new Request("http://google.com")
                .getResource();

        System.out.println(httpResponse.toString());

        assertEquals(200, httpResponse.getResponseCode());
    }

//    @Test
//    public void testPost() throws IOException {
//        Http http = new Http("http://localhost:8080/ping");
//        http.addQueryParameter("foo", "bar");
//        System.out.println(http.doPost("{message: 'hello'}"));
//    }
//
//    @Test
//    public void testPut() throws IOException {
//        Http http = new Http("http://localhost:8080/ping");
//        http.addQueryParameter("foo", "bar");
//        System.out.println(http.doPut("{message: 'hello'}"));
//    }
}
