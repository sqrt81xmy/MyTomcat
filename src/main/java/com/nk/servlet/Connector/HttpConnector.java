package com.nk.servlet.Connector;

import com.nk.ClassLoader.WebAppClassLoader;
//import com.nk.servlet.Filter.FilterMapping.FilterMapping;
//import com.nk.servlet.Filter.OKFilter;
import com.nk.servlet.Impl.HttpExchangeAdapter;
import com.nk.servlet.Impl.HttpServletRequestImpl;
import com.nk.servlet.Impl.HttpServletResponseImpl;
//import com.nk.servlet.Servlet.HelloServlet;
//import com.nk.servlet.Servlet.IndexServlet;
//import com.nk.servlet.Servlet.LoginServlet;
//import com.nk.servlet.Servlet.ServletMapping.ServletMapping;
import com.nk.servlet.ServletContext.ServletContext;
//import com.sun.applet2.Applet2;
//import com.sun.deploy.net.HttpResponse;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.sun.net.httpserver.HttpServer;
import org.apache.commons.cli.ParseException;

import static com.nk.ClassLoader.unzip.ExtractClassPath.ExtractGetWarPath;
import static com.nk.ClassLoader.unzip.ExtractClassPath.unzip;
import static com.nk.ClassLoader.unzip.ExtractClassPath.ExtractAllPaths;
import static java.util.Collections.swap;

public class HttpConnector implements HttpHandler,AutoCloseable {
    final HttpServer httpServer;
    private ServletContext servletContext;
    //从外加载Servlet
    private WebAppClassLoader webAppClassLoader;

    public HttpConnector(String host,int port,String[] args) throws ParseException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //初始化ServletContext
        this.servletContext = new ServletContext();
//        Path path = ExtractGetWarPath(args);
//        String unzip = unzip(path);
        String unzip = "D:\\Users\\SQRT81\\Desktop\\WEB-INF";
        List<String> strings = new ArrayList<>();
        ExtractAllPaths(unzip,strings);
//        swap(strings,8,11);
        //初始化ClassLoader
        this.webAppClassLoader = new WebAppClassLoader();
        String[] stringList = new String[strings.size()];
        Class<?>[] classes = this.webAppClassLoader.loadClasses(strings.toArray(stringList));
        this.servletContext = new ServletContext();
        //初始化filters和servlets
        this.servletContext.initFS(classes);
        //初始化SessionManager
        this.servletContext.initSessionManager();
        //启动Server
        this.httpServer = HttpServer.create(new InetSocketAddress(host, port),0);
        this.httpServer.createContext("/",this);
        this.httpServer.start();
        System.out.println("start jerrymouse http server at"+"  "+host+":  "+port);
    }

    //Servlet写死
//    public HttpConnector(String host,int port) throws IOException {
//        //初始化ServletContext
//        this.servletContext = new ServletContext();
//        //初始化ServletContext:Filter:
//        List<FilterMapping> filterMappings = new ArrayList<>();
//        filterMappings.add(new FilterMapping("/hello(/.*)*",new OKFilter()));
//        this.servletContext.initFilters(filterMappings);
//        //初始化ServletContext:Servlet:
//        List<ServletMapping> httpServletMappings = new ArrayList<>();
//        httpServletMappings.add(new ServletMapping("/hello(/.*)*",new HelloServlet()));
//        httpServletMappings.add(new ServletMapping("/index(/.*)*",new IndexServlet()));
//        httpServletMappings.add(new ServletMapping("/login(/.*)*",new LoginServlet()));
//        this.servletContext.initServlet(httpServletMappings);
//        //初始化ServletContext:SessionManager
//        this.servletContext.initSessionManager();
//        //启动Server
//        this.httpServer = HttpServer.create(new InetSocketAddress(host, port),0);
//        this.httpServer.createContext("/",this);
//        this.httpServer.start();
//        System.out.println("start jerrymouse http server at"+"  "+host+":  "+port);
//    }
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        HttpExchangeAdapter httpExchangeAdapter = new HttpExchangeAdapter(httpExchange);
        HttpServletRequest httpServletRequest = new HttpServletRequestImpl(httpExchangeAdapter,this.servletContext);
        HttpServletResponse httpServletResponse = new HttpServletResponseImpl(httpExchangeAdapter);
        try {
            servletContext.process(httpServletRequest,httpServletResponse);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
    public void process(HttpServletRequest httpServletRequest,
                        HttpServletResponse httpServletResponse) throws IOException {
        //request部分
        String method = httpServletRequest.getMethod();
        String path = httpServletRequest.getPathInfo();
        String query = httpServletRequest.getQueryString();
        System.out.println("{"+method+"}:"+"{"+path+"}"+"{"+query+"}");
        //response部分
        httpServletResponse.addIntHeader("hahaha",200);
        httpServletResponse.setHeader("Content-Type", "text/html; charset=utf-8");
        httpServletResponse.getWriter();
    }

    @Override
    public void close() throws Exception {
        this.httpServer.stop(3);
    }
}
