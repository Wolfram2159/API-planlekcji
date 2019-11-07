package com.wolfram.timetable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@SpringBootApplication
public class TimetableApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimetableApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new JwtFilter());
        filterRegistrationBean.setUrlPatterns(Collections.singleton("/hello/*"));
        return filterRegistrationBean;
    }
}
