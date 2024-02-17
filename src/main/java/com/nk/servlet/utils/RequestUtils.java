package com.nk.servlet.utils;

import java.util.HashMap;
import java.util.Map;

public class RequestUtils {
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
