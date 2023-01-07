package org.example;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface HttpFilter extends Filter {
    @Override
    default public void init(FilterConfig filterConfig) throws ServletException {

    }

    default public boolean isHttp(ServletRequest servletRequest0, ServletResponse servletResponse0) {
        return servletRequest0 instanceof HttpServletRequest && servletResponse0 instanceof HttpServletResponse;
    }

    public void doHttpFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException;

    @Override
    default public void doFilter(ServletRequest servletRequest0, ServletResponse servletResponse0, FilterChain filterChain) throws IOException, ServletException {
        if (isHttp(servletRequest0, servletResponse0)) {
            HttpServletRequest request = (HttpServletRequest) servletRequest0;
            HttpServletResponse response = (HttpServletResponse) servletResponse0;
            doHttpFilter(request, response, filterChain);
        } else {
            filterChain.doFilter(servletRequest0, servletResponse0);
        }

    }

    @Override
    default public void destroy() {

    }
}
