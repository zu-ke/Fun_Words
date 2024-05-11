package com.zk.filter;

import com.zk.utils.JDBCUtilsByDruid;

import javax.servlet.*;
import java.io.IOException;

public class TransactionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            /*放行*/
            filterChain.doFilter(servletRequest, servletResponse);
            JDBCUtilsByDruid.commit();
        } catch (Exception e) {
            JDBCUtilsByDruid.rollback();
            /*将异常抛给tomcat*/
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() {

    }
}
