package com.nk.Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;

@WebServlet("/index(/.*)*")
public class IndexServlet extends HttpServlet implements WebServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Type", "text/html; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write("<h1> Index</h1> <p> Liang Zhuge </p>");
        writer.close();
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public String[] value() {
        return new String[]{"/index(/.*)*"};
    }

    @Override
    public String[] urlPatterns() {
        return new String[]{"/index(/.*)*"};
    }

    @Override
    public int loadOnStartup() {
        return 0;
    }

    @Override
    public WebInitParam[] initParams() {
        return new WebInitParam[0];
    }

    @Override
    public boolean asyncSupported() {
        return false;
    }

    @Override
    public String smallIcon() {
        return null;
    }

    @Override
    public String largeIcon() {
        return null;
    }

    @Override
    public String description() {
        return null;
    }

    @Override
    public String displayName() {
        return null;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
