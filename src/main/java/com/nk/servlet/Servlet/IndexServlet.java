package com.nk.servlet.Servlet;

import com.nk.servlet.Servlet.ServletMapping.AbstractMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Type", "text/html; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write("<h1> Index</h1> <p> Liang Zhuge </p>");
        writer.close();
    }
}
