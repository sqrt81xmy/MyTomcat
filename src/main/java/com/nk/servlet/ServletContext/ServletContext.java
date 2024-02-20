package com.nk.servlet.ServletContext;

import com.nk.Filter.FilterChain.FilterChain;
import com.nk.Filter.FilterMapping.FilterMapping;
import com.nk.Servlet1.ServletMapping.ServletMapping;
import com.nk.servlet.Session.Manager.SessionManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.descriptor.JspConfigDescriptor;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static com.nk.servlet.Constants.LoginConstants.COOKIE;

public class ServletContext implements javax.servlet.ServletContext {

    private SessionManager sessionManager;
    private List<FilterMapping> filterMappings;
    private List<ServletMapping> servletMappings;
    public void initServlet(List<ServletMapping> servletMappings){
        this.servletMappings = servletMappings;
    }

    public void initSessionManager(){
        this.sessionManager = new SessionManager();
    }
    public SessionManager getSessionManager(){
        return this.sessionManager;
    }

    public void initFilters(List<FilterMapping> filterMappings){
        this.filterMappings = filterMappings;
    }

    public void initFS(Class<?>[] classes) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<FilterMapping> filterMappings = new ArrayList<>();
        List<ServletMapping> servletMappings = new ArrayList<>();
        for(Class<?> c:classes){
            if(c.isAnnotationPresent(WebServlet.class)){
                Constructor<?> declaredConstructor = c.getDeclaredConstructor();
                WebServlet s = (WebServlet) declaredConstructor.newInstance();
                Class<? extends Servlet> clazz = (Class<? extends Servlet>) c;
                servletMappings.add(new ServletMapping(s.value()[0], clazz.newInstance()));
            }
            if(c.isAnnotationPresent(WebFilter.class)){
                Constructor<?> declaredConstructor = c.getDeclaredConstructor();
                WebFilter f = (WebFilter) declaredConstructor.newInstance();
                Class<? extends Filter> clazz = (Class<? extends Filter>) c;
                filterMappings.add(new FilterMapping(f.value()[0],clazz.newInstance()));
            }
        }
        this.initFilters(filterMappings);
        this.initServlet(servletMappings);
    }
    public void process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
       //找到一堆恰当的filters组成filterChain
        List<Filter> filters = new ArrayList<>();
        for(FilterMapping s:filterMappings){
            String uri = httpServletRequest.getRequestURI();
            if(s.match(uri)){
                filters.add(s.getFilter());
            }
        }
        FilterChain filterChain = new FilterChain(filters);
        //找到一个恰当的servlet
        assert !servletMappings.isEmpty();
        String uri = httpServletRequest.getRequestURI();
        String cookie = httpServletRequest.getHeader(COOKIE);
        if(cookie==null||cookie.isEmpty()){
            uri = "/login";
        }
        Servlet httpServlet = null;
        for(ServletMapping s:servletMappings){
            if(s.matches(uri)){
                httpServlet = s.getServlet();
                break;
            }
        }
        if(httpServlet==null){
            httpServletResponse.sendError(404);
            return ;
        }
        //执行:filterChain->service
        filterChain.doFilter(httpServletRequest,httpServletResponse);
        httpServlet.service(httpServletRequest,httpServletResponse);
    }

    @Override
    public String getContextPath() {
        return null;
    }

    @Override
    public javax.servlet.ServletContext getContext(String s) {
        return null;
    }

    @Override
    public int getMajorVersion() {
        return 0;
    }

    @Override
    public int getMinorVersion() {
        return 0;
    }

    @Override
    public int getEffectiveMajorVersion() {
        return 0;
    }

    @Override
    public int getEffectiveMinorVersion() {
        return 0;
    }

    @Override
    public String getMimeType(String s) {
        return null;
    }

    @Override
    public Set<String> getResourcePaths(String s) {
        return null;
    }

    @Override
    public URL getResource(String s) throws MalformedURLException {
        return null;
    }

    @Override
    public InputStream getResourceAsStream(String s) {
        return null;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        return null;
    }

    @Override
    public RequestDispatcher getNamedDispatcher(String s) {
        return null;
    }

    @Override
    public Servlet getServlet(String s) throws ServletException {
        return null;
    }

    @Override
    public Enumeration<Servlet> getServlets() {
        return null;
    }

    @Override
    public Enumeration<String> getServletNames() {
        return null;
    }

    @Override
    public void log(String s) {

    }

    @Override
    public void log(Exception e, String s) {

    }

    @Override
    public void log(String s, Throwable throwable) {

    }

    @Override
    public String getRealPath(String s) {
        return null;
    }

    @Override
    public String getServerInfo() {
        return null;
    }

    @Override
    public String getInitParameter(String s) {
        return null;
    }

    @Override
    public Enumeration<String> getInitParameterNames() {
        return null;
    }

    @Override
    public boolean setInitParameter(String s, String s1) {
        return false;
    }

    @Override
    public Object getAttribute(String s) {
        return null;
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return null;
    }

    @Override
    public void setAttribute(String s, Object o) {

    }

    @Override
    public void removeAttribute(String s) {

    }

    @Override
    public String getServletContextName() {
        return null;
    }

    @Override
    public ServletRegistration.Dynamic addServlet(String s, String s1) {
        return null;
    }

    @Override
    public ServletRegistration.Dynamic addServlet(String s, Servlet servlet) {
        return null;
    }

    @Override
    public ServletRegistration.Dynamic addServlet(String s, Class<? extends Servlet> aClass) {
        return null;
    }

    @Override
    public ServletRegistration.Dynamic addJspFile(String s, String s1) {
        return null;
    }

    @Override
    public <T extends Servlet> T createServlet(Class<T> aClass) throws ServletException {
        return null;
    }

    @Override
    public ServletRegistration getServletRegistration(String s) {
        return null;
    }

    @Override
    public Map<String, ? extends ServletRegistration> getServletRegistrations() {
        return null;
    }

    @Override
    public FilterRegistration.Dynamic addFilter(String s, String s1) {
        return null;
    }

    @Override
    public FilterRegistration.Dynamic addFilter(String s, Filter filter) {
        return null;
    }

    @Override
    public FilterRegistration.Dynamic addFilter(String s, Class<? extends Filter> aClass) {
        return null;
    }

    @Override
    public <T extends Filter> T createFilter(Class<T> aClass) throws ServletException {
        return null;
    }

    @Override
    public FilterRegistration getFilterRegistration(String s) {
        return null;
    }

    @Override
    public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
        return null;
    }

    @Override
    public SessionCookieConfig getSessionCookieConfig() {
        return null;
    }

    @Override
    public void setSessionTrackingModes(Set<SessionTrackingMode> set) {

    }

    @Override
    public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
        return null;
    }

    @Override
    public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
        return null;
    }

    @Override
    public void addListener(String s) {

    }

    @Override
    public <T extends EventListener> void addListener(T t) {

    }

    @Override
    public void addListener(Class<? extends EventListener> aClass) {

    }

    @Override
    public <T extends EventListener> T createListener(Class<T> aClass) throws ServletException {
        return null;
    }

    @Override
    public JspConfigDescriptor getJspConfigDescriptor() {
        return null;
    }

    @Override
    public ClassLoader getClassLoader() {
        return null;
    }

    @Override
    public void declareRoles(String... strings) {

    }

    @Override
    public String getVirtualServerName() {
        return null;
    }

    @Override
    public int getSessionTimeout() {
        return 0;
    }

    @Override
    public void setSessionTimeout(int i) {

    }

    @Override
    public String getRequestCharacterEncoding() {
        return null;
    }

    @Override
    public void setRequestCharacterEncoding(String s) {

    }

    @Override
    public String getResponseCharacterEncoding() {
        return null;
    }

    @Override
    public void setResponseCharacterEncoding(String s) {

    }
}
