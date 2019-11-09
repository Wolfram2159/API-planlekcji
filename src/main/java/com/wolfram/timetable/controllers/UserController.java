package com.wolfram.timetable.controllers;

import com.wolfram.timetable.database.entities.User;
import com.wolfram.timetable.database.repositories.UserRepository;
import com.wolfram.timetable.utils.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<String> getWebToken(){
        User u = new User();
        User savedUser = userRepository.save(u);
        String token = Utils.getJWT(savedUser);
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }

}
