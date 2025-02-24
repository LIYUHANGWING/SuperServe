package com.backofli.Filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

/*@WebFilter(urlPatterns = "/*")*/
public class zemoFilter implements Filter {
    @Override//初始化只调用一次
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        System.out.println("初始化了");
    }

    @Override//调用多次
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("拦截了");
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("放行了");
    }

    @Override//销毁法只调用一次
    public void destroy() {
        Filter.super.destroy();
        System.out.println("销毁了");
    }
}
