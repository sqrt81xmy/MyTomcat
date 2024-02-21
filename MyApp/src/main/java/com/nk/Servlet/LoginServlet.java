package com.nk.Servlet;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;

@WebServlet("/login(/.*)*")
public class LoginServlet extends HttpServlet implements WebServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession();//这个初次登陆的往resp也写完了
    }

    @Override
    public String name() {
        return "/login(/.*)*";
    }

    @Override
    public String[] value() {
        return new String[]{"/login(/.*)*"};
    }

    @Override
    public String[] urlPatterns() {
        return new String[]{"/login(/.*)*"};
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
