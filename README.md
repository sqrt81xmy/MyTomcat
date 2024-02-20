### Welcome:
This is the first time that I write the tomcat by myself

For more details, you can refer to this url: https://www.liaoxuefeng.com/wiki/1545956031987744/1545956151525409

## Knowledge:

### Section 2:

In this part, I have implemented the `Tomcat` server which can accept the `servlet` and the `filter` on the other path of my computer. No need to say, it means my server can already execute the client application!This server version could be called `Tomcat2.0`, while the `Tomcat1.0` can only execute the `servlet` on the server, not the client.

#### Error correction:

This part is the error correction of the `Tomcat1.0`, and the error exists in the `Session` part. In the `Tomcat1.0`, the `LoginServlet` needs the `sessionId`(in Cookie) to check whether the user has login(**A session is not created every time the server is accessed, but only when the `HttpRequest.getSession()` method is called**, 这段话的中文是：Session并不是每一次访问服务器都会创建，只有调用`HttpRequest.getSession`方法时才会创建, you can get more details in: https://www.jianshu.com/p/13a1647cc7bc), so I just put the `ServletContext` which can use its `SessionManager` to get the `session`, but if you split your `servlet` and the `server`, you CAN NOT get the `ServerContext` in you `servlet`. In fact you should put the `getSession()` method in the `HttpRequest` class instead of the `ServerContext`(but you can put the `ServletContext` in the `HttpRequest` and use the `SessionManger` in the `ServletContext` to get the `session`), therefore, you can call this method in the function:

```java
public class LoginServlet extends HttpServlet implements WebServlet{
@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession();//这个初次登陆的往resp也写完了
    }
 }
```


