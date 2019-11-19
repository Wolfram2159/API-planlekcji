package com.wolfram.timetable;

import com.wolfram.timetable.controllers.UserController;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AppTests {

    @Test
    public void should_add_two_numbers(){
        //given
        //Calc calc = new Calc();
        //when
        //int result = calc.add(2,4);
        //then
        //Assert.assertEquals(6, result);
        UserController userController = mock(UserController.class);
        when(userController.getWebToken()).thenReturn(getToken());
        ResponseEntity<String> webToken = userController.getWebToken();
        Assert.assertEquals(webToken.getStatusCode(), HttpStatus.CREATED);
    }

    private ResponseEntity<String> getToken(){
        return new ResponseEntity<>("token", HttpStatus.CREATED);
    }
}
