package com.wolfram.timetable.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wolfram.timetable.database.entities.User;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Utils {
    private Utils(){}

    private final static String key = "mySecretKey";

    public static String getJWT(User user){
        long timeNow = System.currentTimeMillis();
        //todo: Save user and get his id, save this in claims, then read id and you know what user you deal with
        return Jwts.builder()
                .claim("id", user.getId())
                .setIssuedAt(new Date(timeNow))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public static Integer getUserId(String token){
        token = token.substring(7);
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        Integer id = (Integer) claims.get("id");
        return id;
    }
    public static String getJson(Object object){
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "";
        try {
            s = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;
    }
}
