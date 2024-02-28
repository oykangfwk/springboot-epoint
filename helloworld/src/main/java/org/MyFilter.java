package org;

import javax.servlet.*;
import java.io.IOException;

public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        ServletContext servletContext = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String request1 = (String)servletRequest.getAttribute("request1");
        if("sso".equals(request1)){
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            servletRequest.getRequestDispatcher("/index").forward(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
