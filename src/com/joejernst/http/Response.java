package com.joejernst.http;

/**
 * Copyright 2012: ux1.tv
 *
 * @author Joe Ernst
 *         Date: 12/16/12
 */
public class Response extends Message<Response> {

    int responseCode;
    String responseMessage;

    public int getResponseCode() {
        return responseCode;
    }

    public Response setResponseCode(int responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public Response setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
        return this;
    }
}
