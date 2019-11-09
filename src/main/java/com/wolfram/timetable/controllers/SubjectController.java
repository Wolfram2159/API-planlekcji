package com.wolfram.timetable.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wolfram.timetable.database.entities.Subject;
import com.wolfram.timetable.database.entities.User;
import com.wolfram.timetable.database.repositories.SubjectRepository;
import com.wolfram.timetable.utils.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Controller
public class SubjectController {
    @Autowired
    private SubjectRepository subjectRepository;

    @PostMapping(value = "/subject")
    public ResponseEntity<String> createSubject(@RequestHeader String authorization, @RequestBody Subject subject) {
        Integer userId = Utils.getUserId(authorization);
        if (subject.getName() == null) {
            return new ResponseEntity<>("Subject without name", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        subject.setUser(new User(userId));
        Subject savedSubject = subjectRepository.save(subject);
        String s = "";
        try {
            s = objectMapper.writeValueAsString(savedSubject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(s, HttpStatus.CREATED);
    }

    @GetMapping(value = "/subject")
    public ResponseEntity<String> getSubjects(@RequestHeader String authorization) {
        Integer userId = Utils.getUserId(authorization);
        List<Subject> subjects = subjectRepository.getSubjectsFromUser(userId);
        String json = Utils.getJson(subjects);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
}
