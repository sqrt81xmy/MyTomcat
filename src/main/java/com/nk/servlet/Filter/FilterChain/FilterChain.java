package com.nk.servlet.Filter.FilterChain;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.List;

public class FilterChain implements javax.servlet.FilterChain {
    private List<Filter> filters;
    private int cnt = 0;

    public FilterChain(List<Filter> filters) {
        this.filters = filters;
        this.cnt = 0;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
        int total = filters.size();
        if(cnt<total){
            cnt++;
            this.filters.get(cnt-1).doFilter(servletRequest,servletResponse,this);
        }
    }
}
