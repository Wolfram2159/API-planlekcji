package com.wolfram.timetable;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtFilter implements javax.servlet.Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String header = httpServletRequest.getHeader("authorization");
        if (!header.startsWith("Bearer ")){
            throw new ServletException("wrong token");
        }else {
            String token = header.substring(7);
            Claims claims = Jwts.parser().setSigningKey(Controller.key).parseClaimsJws(token).getBody();
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
