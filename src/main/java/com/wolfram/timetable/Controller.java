package com.wolfram.timetable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private Repo repo;

    public final static String key = "mySecretKey";

    @GetMapping(value = "/hello")
    public ResponseEntity<String> helloWorld(@RequestHeader String authorization){
        String token = authorization.substring(7);
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        System.out.println(claims);
        Employee e = new Employee();
        e.setName(claims.get("roles").toString());
        repo.save(e);
        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }
    @PostMapping("/login")
    public String getWebToken(@RequestBody User user){
        long timeNow = System.currentTimeMillis();
        //todo: Save user and get his id, save this in claims, then read id and you know what user you deal with
        String token = Jwts.builder()
                .setSubject(user.getLogin())
                .claim("roles", "user")
                .claim("password", user.getPassword())
                .setIssuedAt(new Date(timeNow))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        return token;
    }

}
