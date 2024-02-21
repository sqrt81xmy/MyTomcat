## Welcome to the Tomcat1.0:
This is the first time that I write the tomcat by myself

For more details, you can refer to this url: https://www.liaoxuefeng.com/wiki/1545956031987744/1545956151525409

## Knowledge:
### Section 1:
In this project, I have implemented the `filter`、`connector`、`servlet`、`session`

* #### Summarize the connection between these components:

<div align="center">
  <img src="https://github.com/sqrt81xmy/MyTomcat/assets/89298656/fbb90269-24ae-49db-9e45-77ca3a7b8534" width=350 height=300>
</div>

* filter

  `filter` is used to satisfy some fixed constraints such as "authentication"、"login check"..

  In that case, we use a lot of filters to consist `FilterChain`, and the way of selecting the filters is to match the `RequestUrl` and the  `filter Pattern Url`. In order to execute this chain, it uses the `recursion` called `doFilter`, each filter on this chain executes the `doFilter` fuction, and back to the `recursion` on the chain.
  For example:
  ```
     @Override
      public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
          int total = filters.size();
          if(cnt<total){
              cnt++;
              this.filters.get(cnt-1).doFilter(servletRequest,servletResponse,this);
          }
      }
  ```
   After the `filters[cnt-1]` executes the `doFilter` fuction, it will call the `doFilter` fuction of the chain which refers to the `this` in the last line of the code.


* servlet

    `servlet` is the container of a webapp, only if your app contains one PatternUrl.

     In servlet, it runs one fuction which maps one PatternUrl. For example, a Pattern is `/index(/.*)*`, this pattern represents all the url begin with the `/index`. And if you use the `@RequestMapping("/index(/.*)*")` on the `Index` fuction, this `Index` fuction will run on one servlet. If you have other Url, it will run on the other servlet.

    This is the example:
    ```
      public class IndexServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.setHeader("Content-Type", "text/html; charset=utf-8");
            PrintWriter writer = resp.getWriter();
            writer.write("<h1> Index</h1> <p> Liang Zhuge </p>");
            writer.close();
        }
      }
  //httpServletMappings.add(new ServletMapping("/index(/.*)*",new IndexServlet()));
    ```
    **Trick**:
      This means that one servlet can maps to multiple urls, but one url only maps to one servlet

* connector

    `connector` is the somehow `gateway` of the service, and the starter of the `ServletContext`. And we should know that `http` and `https` will use two different connectors.

   Last but not least, in the connector, we can initialize the `filter`、`servlet`
* session

   A `session` will establish only when a user **first** get/post to your server.

   When a user first connect to the server, your server should build a session for this user, and return a sessionId which is the only one to this session. And in the next connection, the user can send his/her get/post to the server **with** this sessionId, and your server will get the session by this id in the `SessionManager`.

  This sessionId could be `cookie`、`token`...

  
