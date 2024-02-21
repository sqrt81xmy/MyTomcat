## Welcome:
This is the first time that I write the tomcat by myself

For more details, you can refer to this url: https://www.liaoxuefeng.com/wiki/1545956031987744/1545956151525409

## Error correction:

This part is the error correction of the `Tomcat1.0`, and the error exists in the `Session` part. In the `Tomcat1.0`, the `LoginServlet` needs the `sessionId`(in Cookie) to check whether the user has login(**A session is not created every time the server is accessed, but only when the `HttpRequest.getSession()` method is called**, 这段话的中文是：Session并不是每一次访问服务器都会创建，只有调用`HttpRequest.getSession`方法时才会创建, you can get more details in: https://www.jianshu.com/p/13a1647cc7bc), so I just put the `ServletContext` in the `LoginServlet` which can use its `SessionManager` to get the `session`, but if you split your `servlet` and the `server`, you CAN NOT get the `ServerContext` in you `servlet`. In fact you should put the `getSession()` method in the `HttpRequest` class instead of the `ServerContext`(but you can put the `ServletContext` in the `HttpRequest` and use the `SessionManger` in the `ServletContext` to get the `session`), therefore, you can call this method in the function:

```java
public class LoginServlet extends HttpServlet implements WebServlet{
@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession();//这个初次登陆的往resp也写完了
    }
 }
```

## Knowledge:

### Section 2:

In this part, I have implemented the `Tomcat` server which can accept the `servlet` and the `filter` on the other path of my computer. No need to say, it means my server can already execute the client application!This server version could be called `Tomcat2.0`, while the `Tomcat1.0` can only execute the `servlet` on the server, not the client.

To execute the user application in my own server, theres are two tips to notice:

* The user's `servlet` definition must strictly follow the official interface definition, otherwise it cannot be instantiated(用户的`servlet`定义必须严格遵循官方提供的接口定义，否则无法被实例化)
* If there is no errors ,the user's `servlet dependent jar` can not be imported(用户的`servlet`的依赖`jar`包类在无报错情况下可以不用导入)
* Concern about `classpath`: In fact, you only need to read in the `.class` file character stream at the specified location first, and then use the `defineClass` function to generate the corresponding object.(对于`classpath`的担忧：事实上，只需要先读入指定位置的`.class`文件字符流，然后使用`defineClass`函数就可以生成相应的对象)
* The binary package name of "D:/com/nk/servlet.class" is "com.nk.servlet".

And the execute order is: `loadClass`->`findClass`->`defineClass`

Above all, `loadClass` is the most interesting, here is its function:

```java
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
```

First, it checks whether this class has been loaded;

If it has not, it will throw his work to his father and his grandfather;

which is called: 系统加载器(在classpath搜索)、扩展加载器、引导加载器 :

<div align="center">
    <img src="https://github.com/sqrt81xmy/MyTomcat/assets/89298656/d291ea31-3ab0-4559-acbd-a97d3cc295b1" width=500 hight=400>
</div>

<div align="center">
    <img src="https://github.com/sqrt81xmy/MyTomcat/assets/89298656/40cccbca-bb7d-4185-8938-cede3b692c18" width=500 hight=400>
</div>



