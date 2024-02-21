package com.nk.Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;
import java.lang.annotation.Annotation;


@WebFilter("/Liang")
public class OKFilter implements Filter,WebFilter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        int serverPort = servletRequest.getServerPort();
        if(serverPort<80){
            throw new RuntimeException("端口不符!");
        }

        filterChain.doFilter(servletRequest,servletResponse);
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
        return new String[]{"/Liang"};
    }

    @Override
    public String[] urlPatterns() {
        return new String[]{"/Liang"};
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
