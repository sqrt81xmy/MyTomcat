## 欢迎来到Tomcat3.0:

### Tomcat2.0的错误：

在`Tomcat2.0`中，事实上我运行的不是`war`类型的压缩包，而是解压后的文件，这是由于我每次读入压缩包并给他解压之后，这个类都无法正确的读入。今日再看，实际上是因为用压缩流读入->字节输出流读出的时候出现的错误。

首先来看错误的字节读出：

```java
byte[] bytes = new byte[1024];
while((zipInputStream.read(bytes,0,1024))!=-1){
    bufferedOutputStream.write(bytes);
}
```

我之前读的没有压缩的`OkFilter.class`的正常字节数是`2574B`，但是在解压之后就变成了`3072B`了，这是为啥？很明显，`write`方法写了3次，每次写了1024个字节，再×3，显而易见是3072。字节数目不对导致反编译不成功，因此需要提前使用`Available` 方法来获知现在的流里面有多少个字节。只需要把代码改成下面这样就可以了：

```java
byte[] bytes = new byte[zipInputStream.available()];
while((zipInputStream.read(bytes,0,bytes.length))!=-1){
     bufferedOutputStream.write(bytes, 0,bytes.length);
}
```

### 文件打包方法：

 `jar`文件打包方法参考：[idea 打包的jar运行报 “XXX中没有主清单属性”_jar中没有主清单属性-CSDN博客](https://blog.csdn.net/banjing_1993/article/details/83073210?spm=1001.2101.3001.6661.1&utm_medium=distribute.pc_relevant_t0.none-task-blog-2~default~BlogCommendFromBaidu~Rate-1-83073210-blog-52830421.235^v43^pc_blog_bottom_relevance_base4&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2~default~BlogCommendFromBaidu~Rate-1-83073210-blog-52830421.235^v43^pc_blog_bottom_relevance_base4&utm_relevant_index=1)

war文件打包方法：

在`pom.xml` 文件中设置：

<div align="center">
  <img src="https://github.com/sqrt81xmy/MyTomcat/assets/89298656/3d6f882d-fda1-47e3-87d7-eb3654c0b81a" width=300 height=150>
</div>

然后打开`maven`，先`clean` 清空`target` 目录：

<div align="center">
  <img src="https://github.com/sqrt81xmy/MyTomcat/assets/89298656/73564409-73e1-4ddf-921e-123f6a5678e4" width=150 height=200>
</div>

然后`install` 生成即可：

<div align="center">
  <img src="https://github.com/sqrt81xmy/MyTomcat/assets/89298656/c9d9ee38-e1ff-4690-96c9-2cbebcbb1e8f" width=150 height=200>
</div>

### 应用：

你可以下载本目录下的`jar`包和`war`包，然后应用类似cli.txt中的命令运行。

其中`jar` 包是MyTomcat生成的，`war`包是`MyApp`生成的

你就会得到下面这个页面：

<div align="center">
  <img src="https://github.com/sqrt81xmy/MyTomcat/assets/89298656/b070cb6d-39f8-4cc3-9c2f-6a5481118d0c" width=350 height=100>
</div>

访问Servlet中的网址：

<div align="center">
  <img src="https://github.com/sqrt81xmy/MyTomcat/assets/89298656/127fd951-08e7-49ac-805b-2e68154efcd8" width=350 height=130>
</div>
