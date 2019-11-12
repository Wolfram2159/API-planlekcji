package com.wolfram.timetable;

import com.wolfram.timetable.database.entities.Event;
import com.wolfram.timetable.database.entities.Subject;
import com.wolfram.timetable.utils.NullCheckerUtils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
class TimetableApplicationTests {
    // TODO: 2019-11-09 NullCheckerUtils tests
    //NullCheckerUtils tests
    @Test
    void checkFullSubjectTest() {
        Subject subject = new Subject();
        assertTrue(NullCheckerUtils.checkFullSubject(subject));
        subject.setName("");
        assertTrue(NullCheckerUtils.checkFullSubject(subject));
        subject.setId(1);
        assertFalse(NullCheckerUtils.checkFullSubject(subject));
    }
    @Test
    void checkFullEventTest(){
        Event event = new Event();
        assertTrue(NullCheckerUtils.checkFullEvent(event));
        event.setId(1);
        assertTrue(NullCheckerUtils.checkFullEvent(event));
        event.setDay("s");
        assertTrue(NullCheckerUtils.checkFullEvent(event));
        event.setSubject(new Subject(1, "s"));
        assertTrue(NullCheckerUtils.checkFullEvent(event));
        event.setStart_time(new Date());
        assertTrue(NullCheckerUtils.checkFullEvent(event));
        event.setEnd_time(new Date());
        assertTrue(NullCheckerUtils.checkFullEvent(event));
        event.setLocalization("s");
        assertTrue(NullCheckerUtils.checkFullEvent(event));
        event.setDay("Monday");
        assertFalse(NullCheckerUtils.checkFullEvent(event));
    }
    @Test
    void checkDayTest(){
        Event event = new Event();
        event.setDay("");
        assertTrue(NullCheckerUtils.checkDay(event));
        event.setDay("MoN");
        assertTrue(NullCheckerUtils.checkDay(event));
        event.setDay("MONDAY");
        assertFalse(NullCheckerUtils.checkDay(event));
        event.setDay("monday");
        assertFalse(NullCheckerUtils.checkDay(event));
        event.setDay("MoNdAy");
        assertFalse(NullCheckerUtils.checkDay(event));
    }
    @Test
    void checkSubjectTest(){
        Subject subject = new Subject();
        assertTrue(NullCheckerUtils.checkSubject(subject));
        subject.setName("");
        assertFalse(NullCheckerUtils.checkSubject(subject));
        subject.setId(1);
        assertTrue(NullCheckerUtils.checkSubject(subject));
    }
}
