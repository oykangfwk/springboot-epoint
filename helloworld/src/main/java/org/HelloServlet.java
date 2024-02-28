package org;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "HelloServlet", value = "/HelloServlet")
public class HelloServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("name="+config.getInitParameter("name")+",password="+config.getInitParameter("password"));
        System.out.println("servletname="+config.getServletName());
        ServletContext servletContext = config.getServletContext();
        System.out.println(servletContext.getInitParameter("Properties"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Test Servlet Application");
        request.setAttribute("request1","sso");
        //request.getRequestDispatcher("/hello").forward(request,response);

        System.out.println("RemoteHost="+request.getRemoteHost());
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies){
            System.out.println("cookie=="+cookie.getValue()+",name="+cookie.getName());
        }
        HttpSession session = request.getSession();
        System.out.println("isnew=="+session.isNew()+",sessionid="+session.getId());
        session.setAttribute("session1","sessionvalue");
        session.setMaxInactiveInterval(60*60);

        Cookie cookie = new Cookie("test", UUID.randomUUID().toString());
        response.addCookie(cookie);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write("This is response write");
        //response.sendRedirect("http://www.baidu.com");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getAttribute("request1");
    }
}
