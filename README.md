http-client
===========

I wrote this simple Http client to use in my AppEngine applications that use the urlFetch service.  Because it runs
in AppEngine, it can only use the native Java classes (in java.net).

How to use it
-------------
The goal was to make a very simple Http client that I can use to call external RESTful API's.  I wanted to hide all of
the complexities behind an intuitive interface.

Here's an example of a simple Get request:

```java
   Http http = new Http("http://google.com");

   String response = http.doGet();
```

Here's a more complicated example of a PUT request with query parameters and custom headers:

```java
    // Establish a new Http instance, providing the URL as a parameter
    Http http = new Http("http://apiprovider.net/message.json");

    // Add as many query parameters as needed
    http.addQueryParameter("foo", "bar");

    // Add as many headers as needed
    http.addHeader("x-foo-header", "blah");

    // Issue the PUT request to the server, supplying the request body as a String
    String response = http.doPut("{message: 'hello'}");
```
