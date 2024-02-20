package com.nk.ClassLoader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class WebAppClassLoader extends ClassLoader {

    public Class<?>[] loadClasses(String[] names) throws ClassNotFoundException {
        List<Class<?>> clazz = new ArrayList<>();
        for(String name:names){
            clazz.add(loadClass(name));
        }
        Class<?>[] classes = new Class[clazz.size()];
        for(int i=0;i<clazz.size();i++){
            classes[i] = clazz.get(i);
        }
        return classes;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        //查看该类是否已经被加载
        Class<?> loadedClass = findLoadedClass(name);
        if(loadedClass==null){
            if(name.endsWith(".class")){
                return findClass(name);
            }
            else
            {
                if(name.endsWith(".jar")){
                    String[] split = name.split("\\\\");
                    name = split[split.length-1];
                }
                return this.getParent().loadClass(name);
            }
        }
        return loadedClass;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            int[] data_len = new int[1];
            byte[] bytes = getBytes(name,data_len);
            String[] split = name.split("\\\\");
            StringBuilder path = new StringBuilder();
            String separator = ".";
            int flg = 0;
            if(name.endsWith(".class")) {
                for (String s : split) {
                    if (s.equals("com")) {
                        path.append(s);
                        flg = 1;
                    } else if (flg == 1) {
                        path.append(separator);
                        path.append(s);
                    }
                }
                String string = path.toString();
                name = string;
                if (string.endsWith(".class")) {
                    String[] split1 = string.split(".class");
                    name = split1[0];
                }
            }
            return defineClass(name,bytes,0,data_len[0]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] getBytes(String name,int[] datalen) throws IOException {
        FileInputStream is = new FileInputStream(name);
        int len = is.available();
        byte[] bytes = new byte[len];//10K
        is.read(bytes);
        datalen[0] = len;
        is.close();
        return bytes;
    }

    public static URL[] trans(String[] urls) throws MalformedURLException {
        URL[] urls1 = new URL[urls.length];
        for(int i=0;i<urls.length;i++){
            String url = urls[i];
            if (url.endsWith(".class")) {
                String className = url.substring(0, url.length() - 6).replace('/', '.');
            }
            urls1[i] = URI.create("file://" + urls[i]).toURL();
        }
        return urls1;
    }
}
