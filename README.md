http-client
===========

I wrote this simple Http client to use in my AppEngine applications that use the urlFetch service.  Because it runs
in AppEngine, it can only use the native Java classes (in java.net). The goal was to make a very simple Http client that I can use to call external RESTful API's.  I wanted to hide all of
the complexities behind an intuitive interface.

I also used generics so that the method calls can be chained together.

How to use it
-------------

Here's an example of a simple GET request:

```java
        Response httpResponse = new Request()
                .setUrl("http://google.com")
                .getResource();

        String responseBody = httpResponse.getBody();
```

Here's a GET request with a header and a query parameter specified:
```java
        Response httpResponse = new Request()
                .setUrl("http://mysite.com")
                .addHeader("x-my-header", "foobar")
                .addQueryParameter("foo", "bar")
                .getResource();

        String responseBody = httpResponse.getBody();
```

Here's a more complicated example of a POST request with query parameters and a Content-Type header:

```java
        // Posts a simple JSON object to the server
        Response httpResponse = new Request()
                .setUrl("http://mysite.com")
                .addHeader("Content-Type", "application/json")
                .addQueryParameter("foo", "bar")
                .setBody("{foo: 'bar'}")
                .postResource();

        String responseBody = httpResponse.getBody();
```
