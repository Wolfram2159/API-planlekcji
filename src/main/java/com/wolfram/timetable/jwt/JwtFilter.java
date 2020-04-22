package com.wolfram.timetable.jwt;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.NestedServletException;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebMvc
public class JwtFilter implements Filter, WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:3000");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        System.out.println("WebConfig; " + request.getRequestURI());
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Vary", "Origin");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With,observe, Origin, Accept, X-CSRF-TOKEN");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Access-Control-Expose-Headers", "responseType");
        response.addHeader("Access-Control-Expose-Headers", "observe");

        System.out.println("Request Method: " + request.getMethod());
        if (!(request.getMethod().equalsIgnoreCase("OPTIONS"))) {
            String header = request.getHeader("Cookie");
            if (header == null || !header.startsWith("jwt=")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            try {
                chain.doFilter(req, res);
            } catch (NestedServletException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Pre-flight");
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
            response.setHeader("Vary", "Origin");

            // Access-Control-Max-Age
            response.setHeader("Access-Control-Max-Age", "3600");

            // Access-Control-Allow-Credentials
            response.setHeader("Access-Control-Allow-Credentials", "true");

            // Access-Control-Allow-Methods
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");

            // Access-Control-Allow-Headers
            response.setHeader("Access-Control-Allow-Headers",
                    "Access-Control-Expose-Headers," + "Authorization, content-type," +
                            "USERID," + "ROLE," +
                            "access-control-request-headers,access-control-request-method,accept,origin,Authorization,x-requested-with,responseType,observe" + "X-CSRF-TOKEN");
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}