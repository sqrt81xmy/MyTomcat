package com.nk.ClassLoader.unzip;

import org.apache.commons.cli.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ExtractClassPath {
    /**
     * 这个函数用来找到war包的位置
     * */
    public static Path ExtractGetWarPath(String[] args) throws ParseException {
        int len = args.length;
        String Warfile = null;
        for(int i=0;i<len;i++){
            if(args[i].equals("-war")){
                assert i!=(len-1);
                Warfile = args[i+1];
                break;
            }
        }
        assert Warfile != null;
        return Paths.get(Warfile).toAbsolutePath();
    }

    /**
     * 文件解压缩:解压缩.war文件
     */
    public static String unzip(Path WarPath) throws IOException {
        String WarFile = WarPath.toString();
        String DesDirectory = null;
        //找到文件名最后一个dot的位置
        for(int i=WarFile.length()-1;i>=0;i--){
            if(WarFile.charAt(i)=='.'){
                DesDirectory = WarFile.substring(0, i);
                break;
            }
        }
        JarFile warFile = new JarFile(WarFile);
        Enumeration<JarEntry> entries = warFile.entries();
        JarEntry nextEntry = entries.nextElement();
        InputStream zipInputStream = null;
        String Seperator = "/";
        while(nextEntry!=null){
            String fileName = nextEntry.getName();
            while(nextEntry.getName().startsWith("META-INF")){
                if(entries.hasMoreElements())
                    nextEntry = entries.nextElement();
                else
                    break;
            }
            String tarPath = DesDirectory + Seperator + fileName;
            File file = new File(tarPath);
            if(nextEntry.isDirectory()){
                file.mkdirs();
            }
            else if(nextEntry.getName().endsWith(".class")){
                File parentFile = file.getParentFile();
                parentFile.mkdirs();
                file.createNewFile();
                BufferedOutputStream bufferedOutputStream =
                        new BufferedOutputStream(new FileOutputStream(tarPath));
                int len = -1;
                zipInputStream = warFile.getInputStream(nextEntry);
                byte[] bytes = new byte[zipInputStream.available()];
                while((zipInputStream.read(bytes,0,bytes.length))!=-1){
                    bufferedOutputStream.write(bytes, 0,bytes.length);
                }
                bufferedOutputStream.close();
                zipInputStream.close();
            }
            if(entries.hasMoreElements())
                 nextEntry = entries.nextElement();
            else
                break;
            if(nextEntry==null)
                break;
        }

        return DesDirectory;
    }
    /**
     * 递归的提取出所有路径
     */
    public static void ExtractAllPaths(String root, List<String> paths){
        File file = new File(root);
        File[] files = file.listFiles();
        for(File f: files){
            if(f.isDirectory()){
//                paths.add(f.getAbsolutePath());
                ExtractAllPaths(f.getAbsolutePath(),paths);
            }
            else{
                paths.add(f.getAbsolutePath());
            }
        }
    }
}
