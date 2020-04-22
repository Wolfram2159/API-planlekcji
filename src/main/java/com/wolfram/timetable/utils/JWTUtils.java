package com.wolfram.timetable.utils;


import com.wolfram.timetable.database.entities.User;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTUtils {
    private JWTUtils() {
    }

    private final static String key = "mySecretKey";

    public static String getJWT(User user) {
        long timeNow = System.currentTimeMillis();
        return Jwts.builder()
                .claim("id", user.getId())
                .setIssuedAt(new Date(timeNow))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public static Integer getUserId(String token) {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        Integer id = (Integer) claims.get("id");
        return id;
    }
}
