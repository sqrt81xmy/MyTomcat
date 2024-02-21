package com.nk.servlet.Interf;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public interface HttpExchangeResponse {
    int getServerPort();

    public PrintWriter getWriter();
    public void setHeader(String s, String s1);
    void sendResponseHeaders(int i,long t) throws IOException;

    OutputStream getResponseBody();
}
