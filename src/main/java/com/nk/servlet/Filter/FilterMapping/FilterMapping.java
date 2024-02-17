package com.nk.servlet.Filter.FilterMapping;

import javax.servlet.Filter;
import java.util.regex.Pattern;

public class FilterMapping {
    private Pattern urlPattern;
    private Filter filter;
    public FilterMapping(String url,Filter filter){
        this.urlPattern = Pattern.compile(url);
        this.filter = filter;
    }
    public boolean match(String url){
        return this.urlPattern.matcher(url).matches();
    }
    public Filter getFilter(){
        return this.filter;
    }
}
