package com.nk.Servlet1.ServletMapping;


import javax.servlet.Servlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.lang.annotation.Annotation;

public class ServletMapping extends AbstractMapping implements WebServlet {

    private Servlet servlet;
    public ServletMapping(String urlPattern,Servlet servlet) {
        super(urlPattern);
        this.servlet = servlet;
    }
    public ServletMapping(){
        super(null);
        this.servlet = null;
    }
    public Servlet getServlet(){
        return servlet;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public String[] value() {
        return new String[0];
    }

    @Override
    public String[] urlPatterns() {
        return new String[0];
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
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
