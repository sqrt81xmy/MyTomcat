package com.nk.servlet.Servlet.ServletMapping;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;

public class ServletMapping extends AbstractMapping{
    private HttpServlet servlet;
    public ServletMapping(String urlPattern,HttpServlet servlet) {
        super(urlPattern);
        this.servlet = servlet;
    }
    public HttpServlet getServlet(){
        return servlet;
    }
}
