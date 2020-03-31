package com.wolfram.timetable.controllers;

import com.wolfram.timetable.database.entities.Grade;
import com.wolfram.timetable.database.entities.Subject;
import com.wolfram.timetable.database.entities.User;
import com.wolfram.timetable.database.repositories.GradeRepository;
import com.wolfram.timetable.database.repositories.SubjectRepository;
import com.wolfram.timetable.utils.JWTUtils;
import com.wolfram.timetable.utils.JsonCreator;
import com.wolfram.timetable.utils.Responses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Objects;

@Controller
@CrossOrigin
public class GradeController {
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private JsonCreator jsonCreator;

    @PostMapping(value = "/subject/{id}/grade")
    public ResponseEntity<String> createGrade(@RequestHeader String authorization, @PathVariable("id") Integer subjectId, @RequestBody Grade grade) {
        Integer userId = JWTUtils.getUserId(authorization);
        List<Subject> subjectsFromUser = subjectRepository.getSubjectsFromUser(userId);
        if (checkIfNotListContainsSubjectId(subjectsFromUser, subjectId)) {
            return new ResponseEntity<>(Responses.FORBIDDEN, HttpStatus.FORBIDDEN);
        }
        if (grade.checkIfNotHaveNecessaryFields()) {
            return new ResponseEntity<>(Responses.UNPROCESSABLE_ENTITY, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        grade.setId(null);
        grade.setSubject(new Subject(subjectId));
        grade.setUser(new User(userId));
        Grade save = gradeRepository.save(grade);
        String json = jsonCreator.createJsonForObject(save);
        return new ResponseEntity<>(json, HttpStatus.CREATED);
    }

    private boolean checkIfNotListContainsSubjectId(List<Subject> subjectList, Integer subjectId) {
        for (Subject subject : subjectList) {
            if (subject.getId().equals(subjectId)) {
                return false;
            }
        }
        return true;
    }

    @GetMapping(value = "/subject/{id}/grade")
    public ResponseEntity<String> getGradesFromSubject(@RequestHeader String authorization, @PathVariable("id") Integer subjectId) {
        Integer userId = JWTUtils.getUserId(authorization);
        Subject subject = subjectRepository.getOne(subjectId);
        if (!userId.equals(subject.getUser().getId())) {
            return new ResponseEntity<>(Responses.FORBIDDEN, HttpStatus.FORBIDDEN);
        }
        List<Grade> gradesFromUser = gradeRepository.getGradesFromSubject(userId, subjectId);
        String json = jsonCreator.createJsonForObject(gradesFromUser);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @DeleteMapping(value = "/grade/{id}")
    public ResponseEntity<String> deleteGrade(@RequestHeader String authorization, @PathVariable("id") Integer gradeId) {
        Integer userId = JWTUtils.getUserId(authorization);
        List<Grade> gradesFromUser = gradeRepository.getGradesFromUser(userId);
        if (checkIfNotListContainsEvent(gradesFromUser, gradeId)) {
            return new ResponseEntity<>(Responses.FORBIDDEN, HttpStatus.FORBIDDEN);
        }
        Grade gradeToDelete = new Grade();
        gradeToDelete.setId(gradeId);
        gradeRepository.delete(gradeToDelete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/subject/{id}/grade/{gradeId}")
    public ResponseEntity<String> updateGrade(@RequestHeader String authorization, @PathVariable("id") Integer subjectId, @PathVariable("gradeId") Integer gradeId, @RequestBody Grade grade) {
        Integer userId = JWTUtils.getUserId(authorization);
        List<Subject> subjectsFromUser = subjectRepository.getSubjectsFromUser(userId);
        if (checkIfNotListContainsSubjectId(subjectsFromUser, subjectId)) {
            return new ResponseEntity<>(Responses.FORBIDDEN, HttpStatus.FORBIDDEN);
        }
        if (grade.checkIfNotHaveNecessaryFields()) {
            return new ResponseEntity<>(Responses.UNPROCESSABLE_ENTITY, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Grade gradeFromRepo = gradeRepository.getOne(gradeId);
        Integer userIdFromRepo = gradeFromRepo.getUser().getId();
        if (!userIdFromRepo.equals(userId)) {
            return new ResponseEntity<>(Responses.FORBIDDEN, HttpStatus.FORBIDDEN);
        }
        grade.setUser(new User(userId));
        grade.setSubject(new Subject(subjectId));
        grade.setId(gradeId);
        Grade save = gradeRepository.save(grade);
        String json = jsonCreator.createJsonForObject(save);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    private boolean checkIfNotListContainsEvent(List<Grade> gradesFromUser, Integer searchingGradeId) {
        for (Grade grade : gradesFromUser) {
            if (Objects.equals(grade.getId(), searchingGradeId)) {
                return false;
            }
        }
        return true;
    }
}