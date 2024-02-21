package com.nk;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class MyTomcat implements HttpHandler, AutoCloseable  {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        //获取请求方法+请求路径
        String requestMethod = httpExchange.getRequestMethod();
        URI requestURI = httpExchange.getRequestURI();
        String path = requestURI.getPath();
        String rawQuery = requestURI.getRawQuery();
        System.out.println("{"+requestMethod+"}:"+"{"+path+"}"+"{"+rawQuery+"}");
        //输出响应header
        Headers responseHeaders = httpExchange.getResponseHeaders();
        responseHeaders.set("love:","Liang");
        responseHeaders.set("Content-Type", "text/html; charset=utf-8");
        //设置200响应
        httpExchange.sendResponseHeaders(200,0);
        //设置响应内容
        String s = "<h1>Liang Zhuge</h1>";
        //加了try之后会自动关闭资源
        try(OutputStream responseBody = httpExchange.getResponseBody()) {
            responseBody.write(s.getBytes(StandardCharsets.UTF_8));
        }
    }

    public static void main(String[] args) {
        String host = "0.0.0.0";
        int port = 8082;
        try (MyTomcat connector = new MyTomcat(host, port)) {
            for (;;) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    final HttpServer httpServer;
    final String host;
    final int port;

    public MyTomcat(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        this.httpServer = HttpServer.create(new InetSocketAddress(host, port),0);
        this.httpServer.createContext("/",this);
        this.httpServer.start();
        System.out.println("start jerrymouse http server at"+"  "+host+":  "+port);
    }

    public void close() {
        this.httpServer.stop(3);
    }
}
