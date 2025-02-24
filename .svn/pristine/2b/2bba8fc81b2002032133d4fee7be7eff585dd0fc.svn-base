package com.backofli.Filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

/*@WebFilter(urlPatterns = "/*")*/
public class zbcFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("拦截了");
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("放行了");
    }
}
