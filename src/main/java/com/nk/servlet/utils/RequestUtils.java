package com.nk.servlet.utils;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class RequestUtils extends URLClassLoader {
    public RequestUtils(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public static Map<String,String> parseQuery(String params){
        Map<String,String> mp = new HashMap<>();
        for(String param_pair:params.split("&")){
            String[] param = param_pair.split("=");
            if(param.length==2){
                mp.put(param[0],param[1]);
            }
            else
                mp.put(param[0],"");
        }
        return mp;
    }
}
