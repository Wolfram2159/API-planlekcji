package com.wolfram.timetable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wolfram.timetable.controllers.SubjectController;
import com.wolfram.timetable.controllers.UserController;
import com.wolfram.timetable.database.entities.Subject;
import com.wolfram.timetable.utils.JsonCreator;
import com.wolfram.timetable.utils.Responses;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AppTests {

    private SubjectController subjectController;

    @Test
    public void getJWT() {
        UserController userController = mock(UserController.class);
        when(userController.getWebToken()).thenReturn(getToken());
        ResponseEntity<String> webToken = userController.getWebToken();
        Assert.assertEquals(webToken.getStatusCode(), HttpStatus.CREATED);
    }

    private ResponseEntity<String> getToken() {
        return new ResponseEntity<>("token", HttpStatus.CREATED);
    }

    @Before
    public void initController(){
        subjectController = mock(SubjectController.class);
    }

    /*@Test
    public void shouldReturnUnprocessableEntityStatusOnPost(){
        //SubjectController subjectController = mock(SubjectController.class);
        Subject subject = new Subject((String) null);
        when(subjectController.createSubject("JWT", subject)).thenReturn(returnUnprocessable());
        ResponseEntity<String> response = subjectController.createSubject("JWT", subject);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
        Assert.assertEquals(response.getBody(), Responses.UNPROCESSABLE_ENTITY);
    }

    private ResponseEntity<String> returnUnprocessable(){
        return new ResponseEntity<>(Responses.UNPROCESSABLE_ENTITY, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void shouldReturnCreatedStatusOnPost(){
        when(subjectController.createSubject(eq(""), any(Subject.class))).thenReturn(returnCreated());
        ResponseEntity<String> response = subjectController.createSubject("", new Subject(""));
        Assert.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }*/

    private ResponseEntity<String> returnCreated(){
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }

    @Test
    public void shouldReturnOkStatusOnGet(){
        when(subjectController.getSubjects(eq(""))).thenReturn(returnOk());
        ResponseEntity<String> response = subjectController.getSubjects("");
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    private ResponseEntity<String> returnOk(){
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
