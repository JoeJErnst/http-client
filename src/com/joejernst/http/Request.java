package com.joejernst.http;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2012 Joe J. Ernst
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class Request extends Message<Request> {

    HttpURLConnection connection;
    OutputStreamWriter writer;

    URL url;
    Map<String, String> query = new HashMap<String, String>();

    public Request addQueryParameter(String name, String value) {
        this.query.put(name, value);
        return this;
    }

    public Request removeQueryParameter(String name) {
        this.query.remove(name);
        return this;
    }

    public Request setUrl(String url) throws MalformedURLException {
        this.url = new URL(url);
        return this;
    }

    public Response getResource() throws IOException {
        buildQueryString();
        buildHeaders();

        connection = (HttpURLConnection) this.url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("GET");

        return readResponse();
    }

    public Response putResource() throws IOException {
        return writeResource("PUT", this.body);
    }

    public Response postResource() throws IOException {
        return writeResource("POST", this.body);
    }

    public Response deleteResource() throws IOException {
        buildQueryString();
        buildHeaders();

        connection = (HttpURLConnection) this.url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("DELETE");

        return readResponse();
    }

    private Response writeResource(String method, String message) throws IOException {
        buildQueryString();
        buildHeaders();

        try {
            message = URLEncoder.encode(message, "UTF-8");
        } catch (UnsupportedEncodingException uee) {
            // this should never happen because we're sticking with UTF-8 encoding
            uee.printStackTrace();
        }

        connection = (HttpURLConnection) this.url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod(method);

        writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write("message=" + message);
        writer.close();

        return readResponse();
    }

    private Response readResponse() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        StringBuilder builder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        reader.close();

        return new Response()
                .setResponseCode(connection.getResponseCode())
                .setResponseMessage(connection.getResponseMessage())
                .setBody(builder.toString());
    }

    private void buildQueryString() throws MalformedURLException {
        StringBuilder builder = new StringBuilder();

        // Put the query parameters on the URL before issuing the request
        if (!query.isEmpty()) {
            for (Map.Entry param : query.entrySet()) {
                builder.append(param.getKey());
                builder.append("=");
                builder.append(param.getValue());
                builder.append("&");
            }
            builder.deleteCharAt(builder.lastIndexOf("&")); // Remove the trailing ampersand
        }

        if (builder.length() > 0) {
            // If there was any query string at all, begin it with the question mark
            builder.insert(0, "?");
        }

        url = new URL(url.toString() + builder.toString());
    }

    private void buildHeaders() {
        if (!headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                connection.addRequestProperty(entry.getKey(), entry.getValue());
            }
        }

    }

}
