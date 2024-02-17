package com.nk.servlet.Servlet;

import com.nk.servlet.ServletContext.ServletContext;
import com.nk.servlet.Session.HttpSessionImpl;
import com.sun.deploy.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static com.nk.servlet.Constants.LoginConstants.*;
import static com.nk.servlet.utils.RequestUtils.parseQuery;

public class LoginServlet extends HttpServlet {

    private ServletContext servletContext;
    public LoginServlet(ServletContext servletContext){
        this.servletContext = servletContext;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String params = req.getParameter("");
        String id = "11";
        String pwd = "22";
        if(params!=null){
            Map<String,String> mp = parseQuery(params);
            id = mp.get(LOGIN_USER);
            pwd = mp.get(LOGIN_PWD);
        }
        String header = req.getHeader(COOKIE);
        boolean iscreate = (header==null||header.length()==0)?false:true;
        if(iscreate){
            throw new RuntimeException("重复登录?");
        }
        //TODO:连数据库
        if(id!=null&&pwd!=null){
            //TODO:SessionId加密
            resp.setHeader(COOKIE,id);
            //添加session
            HttpSession session = new HttpSessionImpl(id);
            this.servletContext.getSessionManager().addSession(id,session);
            //往resp里面加点字
            PrintWriter writer = resp.getWriter();
            writer.write("<h1>Login Liang</h1>");
            writer.close();
        }
        else
            throw new RuntimeException("登录格式错误！");
    }


}
