package com.nk.ClassLoader.unzip;

import org.apache.commons.cli.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(WarPath.toString()));
        ZipEntry nextEntry = zipInputStream.getNextEntry();
        String Seperator = "/";
        while(nextEntry!=null){
            String fileName = nextEntry.getName();
            String tarPath = DesDirectory + Seperator + fileName;
            File file = new File(tarPath);
            if(nextEntry.isDirectory()){
                file.mkdirs();
            }
            else{
                File parentFile = file.getParentFile();
                parentFile.mkdirs();
                file.createNewFile();
                BufferedOutputStream bufferedOutputStream =
                        new BufferedOutputStream(new FileOutputStream(tarPath));
                int len = -1;
                byte[] bytes = new byte[1024];
                while((zipInputStream.read(bytes,0,1024))!=-1){
                    bufferedOutputStream.write(bytes);
                }
                bufferedOutputStream.close();
            }
            zipInputStream.closeEntry();
            nextEntry = zipInputStream.getNextEntry();
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
