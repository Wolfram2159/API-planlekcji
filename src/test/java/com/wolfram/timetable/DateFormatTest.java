package com.wolfram.timetable;

import com.wolfram.timetable.database.entities.Event;
import com.wolfram.timetable.database.entities.Grade;

import org.junit.Assert;
import org.junit.Test;

public class DateFormatTest {
    @Test
    public void validFormatTest() {
        String correctFormat = "15:23";
        String incorrectFormat = "xd:xd";
        Assert.assertTrue(Event.isValidFormat(correctFormat));
        Assert.assertFalse(Event.isValidFormat(incorrectFormat));
    }

    @Test
    public void gradeValidFormat() {
        String correctFormat = "15-03-2020";
        String incorrectForamt = "xd-03-2020";
        Assert.assertTrue(Grade.isValidFormat(correctFormat));
        Assert.assertFalse(Grade.isValidFormat(incorrectForamt));
    }

    @Test
    public void isNullPointerException() {
        String empty = null;
        Grade gradeWithEmptyDate = new Grade();
        gradeWithEmptyDate.setDate(empty);
        gradeWithEmptyDate.setDescription("");
        Assert.assertTrue(gradeWithEmptyDate.checkIfNotHaveNecessaryFields());
    }
}
