package com.wolfram.timetable.controllers;

import com.wolfram.timetable.database.entities.Grade;
import com.wolfram.timetable.database.entities.User;
import com.wolfram.timetable.database.repositories.GradeRepository;
import com.wolfram.timetable.utils.JWTUtils;
import com.wolfram.timetable.utils.JsonCreator;
import com.wolfram.timetable.utils.NullCheckerUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.Null;

@Controller
public class GradeController {
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private JsonCreator jsonCreator;

    @PostMapping(value = "/grade")
    public ResponseEntity<String> createGrade(@RequestHeader String authorization, @RequestBody Grade grade){
        Integer userId = JWTUtils.getUserId(authorization);
        if (NullCheckerUtils.checkGrade(grade)) {
            return new ResponseEntity<>("Grade without necessary fields.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        User user = new User(userId);
        grade.setUser(user);
        Grade savedGrade = gradeRepository.save(grade);
        String json = jsonCreator.createJsonForObject(savedGrade);
        return new ResponseEntity<>(json, HttpStatus.CREATED);
    }

    @GetMapping(value = "/grade/subject/{id}")
    public ResponseEntity<String> getGradesFromSubject(@RequestHeader String authorization, @PathVariable Integer id){
        Integer userId = JWTUtils.getUserId(authorization);
        if (id == null){
            return new ResponseEntity<>("No subject id.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        List<Grade> gradesFromUser = gradeRepository.getGradesFromSubject(userId, id);
        String json = jsonCreator.createJsonForObject(gradesFromUser);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @DeleteMapping(value = "/grade/{id}")
    public ResponseEntity<String> deleteGrade(@RequestHeader String authorization, @PathVariable Integer id){
        Integer userId = JWTUtils.getUserId(authorization);
        if (id == null){
            return new ResponseEntity<>("No grade id.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        List<Grade> gradesFromUser = gradeRepository.getGradesFromUser(userId);

        if (!checkIfListContainsEvent(gradesFromUser, userId)){
            return new ResponseEntity<>("You have no permissions for this record.", HttpStatus.FORBIDDEN);
        }
        Grade gradeToDelete = new Grade();
        gradeToDelete.setId(id);
        gradeRepository.delete(gradeToDelete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/grade")
    public ResponseEntity<String> updateGrade(@RequestHeader String authorization, @RequestBody Grade grade){
        if (NullCheckerUtils.checkFullGrade(grade)){
            return new ResponseEntity<>("Invalid data", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Integer userId = JWTUtils.getUserId(authorization);
        List<Grade> gradesFromUser = gradeRepository.getGradesFromUser(userId);
        if (!checkIfListContainsEvent(gradesFromUser, grade.getId())){
            return new ResponseEntity<>("You have no permissions for this record.", HttpStatus.FORBIDDEN);
        }
        User user = new User(userId);
        grade.setUser(user);
        Grade save = gradeRepository.save(grade);
        String json = jsonCreator.createJsonForObject(save);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    private boolean checkIfListContainsEvent(List<Grade> gradesFromUser, Integer searchingGradeId){
        for (Grade grade : gradesFromUser) {
            if (Objects.equals(grade.getId(), searchingGradeId)){
                return true;
            }
        }
        return false;
    }
}
