package com.joejernst.http;

import java.util.HashMap;
import java.util.Map;

/**
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

public abstract class Message<T extends Message<T>> {
    Map<String, String> headers = new HashMap<String, String>();
    String body;

    public String getBody() {
        return this.body;
    }

    @SuppressWarnings("unchecked")
    public T setBody(String body) {
        this.body = body;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T addHeader(String name, String value) {
        this.headers.put(name, value);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T removeHeader(String name) {
        this.headers.remove(name);
        return (T) this;
    }


}
