package com.joejernst;

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

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Http {
    HttpURLConnection connection;
    OutputStreamWriter writer;

    URL url;
    Map<String, String> query = new HashMap<String, String>();
    Map<String, String> headers = new HashMap<String, String>();
    String body;

    int responseCode;
    String responseMessage;

    public Http(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    public String doGet() throws IOException {
        buildQueryString();
        buildHeaders();

        connection = (HttpURLConnection) this.url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        StringBuilder builder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        reader.close();

        this.responseCode = connection.getResponseCode();
        this.responseMessage = connection.getResponseMessage();

        return builder.toString();
    }

    public void doDelete() throws IOException {
        buildQueryString();
        buildHeaders();

        connection = (HttpURLConnection) this.url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("DELETE");

        this.responseCode = connection.getResponseCode();
        this.responseMessage = connection.getResponseMessage();
    }

    public String doPut(String message) throws IOException {
        return doWrite("PUT", message);
    }

    public String doPost(String message) throws IOException {
        return doWrite("POST", message);
    }

    private String doWrite(String method, String message) throws IOException {
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

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        StringBuilder builder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        reader.close();

        this.responseCode = connection.getResponseCode();
        this.responseMessage = connection.getResponseMessage();

        return builder.toString();
    }

    private void buildHeaders() {
        if(!headers.isEmpty()) {
            for(Map.Entry<String, String> entry : headers.entrySet()) {
                connection.addRequestProperty(entry.getKey(), entry.getValue());
            }
        }

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

        url = new URL(url.toString() +  builder.toString());
    }

    // Convenience method to add a single header (or change the value of an existing header)
    public String addHeader(String name, String value) {
        return headers.put(name, value);
    }

    // Convenience method for removing a specific header
    public String removeHeader(String name) {
        return headers.remove(name);
    }

    // Convenience method to add a single query parameter (or change the value of an existing query parameter)
    public String addQueryParameter(String name, String value) {
        return query.put(name, value);
    }

    // Convenience method for removing a specific query parameter
    public String removeQueryParameter(String name) {
        return query.remove(name);
    }


    // Getters and Setters
    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public Map getQuery() {
        return query;
    }

    public void setQuery(Map<String, String> query) {
        this.query = query;
    }

    public Map getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
