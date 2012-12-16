http-client
===========

I wrote this simple Http client to use in my AppEngine applications that use the urlFetch service.  Because it runs
in AppEngine, it can only use the native Java classes (in java.net).

How to use it
-------------
The goal was to make a very simple Http client that I can use to call external RESTful API's.  I wanted to hide all of
the complexities behind an intuitive interface.  Using Http is as simple as following these steps:

1.  Instantiate a new Http object, passing a String representation of the URL as the only parameter.  The URL may or may
not contain query string parameters at this point.
2.  Add any headers you need
3.  Add any query parameters you need
4.  Issue the request (include the body as a parameter for Post and Put requests)

```java
    Http http = new Http("http://apiprovider.net/api");
    http.addQueryParameter("foo", "bar");
    http.addHeader("x-foo-header", "blah");
    System.out.println(http.doPut("{message: 'hello'}"));
```
