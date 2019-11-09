package com.wolfram.timetable.controllers;

import com.wolfram.timetable.database.entities.Subject;
import com.wolfram.timetable.utils.Utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
public class SubjectController {
    /*@GetMapping(value = "/hello")
        public ResponseEntity<String> helloWorld(@RequestHeader String authorization){
            Integer userId = Utils.getUserId(authorization);

            return new ResponseEntity<>("Hello World!", HttpStatus.OK);
        }*/
    @PostMapping(value = "/subject")
    public ResponseEntity<String> createSubject(@RequestHeader String authorization, @RequestBody Subject subject) {
        Integer userId = Utils.getUserId(authorization);
        if (subject.getName() == null) {
            //return new ResponseEntity<>("Wrong subject", HttpStatus.)
        }
        return new ResponseEntity<>("ok", HttpStatus.CREATED);
    }

    @GetMapping(value = "/subject")
    public ResponseEntity<String> getSubjects(@RequestHeader String authorization) {

        return new ResponseEntity<>("gitgut", HttpStatus.OK);
    }
}
