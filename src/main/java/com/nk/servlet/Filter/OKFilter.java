package com.nk.servlet.Filter;

import javax.servlet.*;
import java.io.IOException;

import static com.nk.servlet.Constants.LoginConstants.COOKIE;

public class OKFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        int serverPort = servletRequest.getServerPort();
        if(serverPort<80){
            throw new RuntimeException("端口不符!");
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }
}
