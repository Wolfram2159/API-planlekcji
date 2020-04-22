package com.wolfram.timetable.controllers;

import com.wolfram.timetable.database.entities.User;
import com.wolfram.timetable.database.repositories.UserRepository;
import com.wolfram.timetable.utils.JWTUtils;
import com.wolfram.timetable.utils.Responses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<String> getWebToken(HttpServletResponse response, @RequestBody User user) {
        User savedUser = userRepository.getUser(user.getUsername(), user.getPassword());
        if (savedUser == null)
            return new ResponseEntity<>("Wrong username or password", HttpStatus.UNAUTHORIZED);
        String token = JWTUtils.getJWT(savedUser);
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(HttpServletResponse response, @RequestBody User user) {
        List<User> allUsers = userRepository.getAllUsers();
        if (isUserExist(user, allUsers))
            return new ResponseEntity<>("User already exists" ,HttpStatus.CONFLICT);
        User savedUser = userRepository.save(user);
        String token = JWTUtils.getJWT(savedUser);
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }

    private boolean isUserExist(User newUser, List<User> users) {
        for (User user : users) {
            if (user.getUsername().equals(newUser.getUsername())) return true;
        }
        return false;
    }
}