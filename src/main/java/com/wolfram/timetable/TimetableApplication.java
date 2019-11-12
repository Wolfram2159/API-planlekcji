package com.wolfram.timetable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wolfram.timetable.jwt.JwtFilter;
import com.wolfram.timetable.utils.JsonCreator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

// TODO: 2019-11-09 put
// TODO: 2019-11-09 date now
// TODO: 2019-11-09

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
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/hello/*", "/subject/*","/event/*","/grade/*","/note/*"));
        return filterRegistrationBean;
    }
    @Bean
    public JsonCreator creator(){
        ObjectMapper objectMapper = new ObjectMapper();
        return new JsonCreator(objectMapper);
    }
}
