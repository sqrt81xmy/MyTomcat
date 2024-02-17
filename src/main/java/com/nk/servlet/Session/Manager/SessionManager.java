package com.nk.servlet.Session.Manager;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private Map<String,HttpSession> sessionList;
    public SessionManager(){
        this.sessionList = new HashMap<>();
    }
    public SessionManager(Map<String,HttpSession> sessionList){
        this.sessionList = sessionList;
    }
    public void addSession(String SessionId,HttpSession httpSession){
        sessionList.put(SessionId,httpSession);
    }
}
