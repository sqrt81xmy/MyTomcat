package com.nk.servlet.Impl;

import com.nk.servlet.Interf.HttpExchangeRequest;
import com.nk.servlet.Interf.HttpExchangeResponse;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * 把adapter往HttpServletRequestImpl
 * 和HttpServletResponseImpl里面传
 * 让adapter实现这两个的功能
 *
 * */
public class HttpExchangeAdapter implements HttpExchangeResponse, HttpExchangeRequest {
    private HttpExchange httpExchange;
    public HttpExchangeAdapter(HttpExchange httpExchange){
        this.httpExchange = httpExchange;
    }

    @Override
    public String getMethod() {
        return httpExchange.getRequestMethod();
    }

    @Override
    public String getRequestURI() {
        return httpExchange.getRequestURI().toString();
    }

    @Override
    public String getPathInfo() {
        return httpExchange.getRequestURI().getPath();
    }

    @Override
    public String getQueryString() {
        return httpExchange.getRequestURI().getQuery();
    }

    @Override
    public int getServerPort() {
        return -1;
    }

    @Override
    public String getParameter(String s) {
        return httpExchange.getRequestURI().getRawQuery();
    }

    @Override
    public String getHeader(String s) {
        Headers requestHeaders = httpExchange.getRequestHeaders();
        List<String> strings = requestHeaders.get(s);
        if(strings==null)
            return null;
        return strings.get(0);
    }


    @Override
    public PrintWriter getWriter() {
        return null;
        //return httpExchange.getResponseBody();
    }

    public OutputStream getWriter1() {
        return httpExchange.getResponseBody();
    }

    @Override
    public void setHeader(String s, String s1) {
        httpExchange.getResponseHeaders().set(s,s1);
    }

    @Override
    public void sendResponseHeaders(int i, long t) throws IOException {
        httpExchange.sendResponseHeaders(i,t);
    }

    @Override
    public OutputStream getResponseBody() {
        return httpExchange.getResponseBody();
    }
}
