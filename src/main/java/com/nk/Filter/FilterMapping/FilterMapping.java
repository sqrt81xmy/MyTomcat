package com.nk.Filter.FilterMapping;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.lang.annotation.Annotation;
import java.util.regex.Pattern;

public class FilterMapping implements WebFilter {
    private Pattern urlPattern;
    private Filter filter;
    public FilterMapping(){
        this.urlPattern = null;
        this.filter = null;
    }
    public FilterMapping(String url,Filter filter){
        this.urlPattern = Pattern.compile(url);
        this.filter = filter;
    }
    public boolean match(String url){
        return this.urlPattern.matcher(url).matches();
    }
    public Filter getFilter(){
        return this.filter;
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
    public WebInitParam[] initParams() {
        return new WebInitParam[0];
    }

    @Override
    public String filterName() {
        return null;
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
    public String[] servletNames() {
        return new String[0];
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
    public DispatcherType[] dispatcherTypes() {
        return new DispatcherType[0];
    }

    @Override
    public boolean asyncSupported() {
        return false;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
