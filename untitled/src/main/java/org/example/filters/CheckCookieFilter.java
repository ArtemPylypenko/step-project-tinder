package org.example.filters;

import org.example.filters.HttpFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class CheckCookieFilter implements HttpFilter {
    @Override
    public void doHttpFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Cookie[] cookies = request.getCookies();
        Optional<Cookie> cookieId = Optional.ofNullable(cookies)
                .flatMap(cookies1 ->
                        Arrays.stream(cookies1).filter(cookie ->
                                cookie.getName().equals("id")).findFirst());
        if (cookieId.isPresent())
            filterChain.doFilter(request, response);
        else
            response.sendRedirect("/login");
    }
}
