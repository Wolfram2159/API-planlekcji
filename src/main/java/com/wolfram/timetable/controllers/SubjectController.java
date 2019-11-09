package com.wolfram.timetable.controllers;

import com.wolfram.timetable.database.entities.Subject;
import com.wolfram.timetable.database.entities.User;
import com.wolfram.timetable.database.repositories.SubjectRepository;
import com.wolfram.timetable.utils.JsonCreator;
import com.wolfram.timetable.utils.JWTUtils;
import com.wolfram.timetable.utils.NullCheckerUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Objects;

@Controller
public class SubjectController {
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private JsonCreator jsonCreator;

    @PostMapping(value = "/subject")
    public ResponseEntity<String> createSubject(@RequestHeader String authorization, @RequestBody Subject subject) {
        if (NullCheckerUtils.checkSubject(subject)) {
            return new ResponseEntity<>("Subject without name", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Integer userId = JWTUtils.getUserId(authorization);
        subject.setUser(new User(userId));
        Subject savedSubject = subjectRepository.save(subject);
        String json = jsonCreator.createJsonForObject(savedSubject);
        return new ResponseEntity<>(json, HttpStatus.CREATED);
    }

    @GetMapping(value = "/subject")
    public ResponseEntity<String> getSubjects(@RequestHeader String authorization) {
        Integer userId = JWTUtils.getUserId(authorization);
        List<Subject> subjects = subjectRepository.getSubjectsFromUser(userId);
        String json = jsonCreator.createJsonForObject(subjects);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @DeleteMapping(value = "/subject/{id}")
    public ResponseEntity<String> deleteSubject(@RequestHeader String authorization, @PathVariable Integer id) {
        Integer userId = JWTUtils.getUserId(authorization);
        if (id == null) {
            return new ResponseEntity<>("No subject id.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        List<Subject> eventsFromUser = subjectRepository.getSubjectsFromUser(userId);

        if (!checkIfListContainsEvent(eventsFromUser, id)) {
            return new ResponseEntity<>("You have no permissions for this record.", HttpStatus.FORBIDDEN);
        }

        Subject subjectToDelete = new Subject();
        subjectToDelete.setId(id);
        subjectRepository.delete(subjectToDelete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private boolean checkIfListContainsEvent(List<Subject> subjectsFromUser, Integer searchingSubjectId) {
        for (Subject event : subjectsFromUser) {
            if (Objects.equals(event.getId(), searchingSubjectId)) {
                return true;
            }
        }
        return false;
    }
}
