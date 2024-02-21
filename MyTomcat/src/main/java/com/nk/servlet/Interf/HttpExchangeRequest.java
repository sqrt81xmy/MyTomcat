package com.nk.servlet.Interf;

import java.util.Map;

public interface HttpExchangeRequest {
    public String getMethod();
    public String getRequestURI();
    public String getPathInfo();
    public String getQueryString();
    public String getParameter(String s);
    public String getHeader(String s);
}
