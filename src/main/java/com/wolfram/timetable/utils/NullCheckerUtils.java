package com.wolfram.timetable.utils;

import com.wolfram.timetable.database.entities.Event;
import com.wolfram.timetable.database.entities.Grade;
import com.wolfram.timetable.database.entities.Subject;

public class NullCheckerUtils {
    private NullCheckerUtils() {
    }

    public static boolean checkSubject(Subject subject) {
        return (subject.getName() == null || subject.getId() != null);
    }

    public static boolean checkEvent(Event event) {
        if (event == null || event.getSubject() == null) {
            return true;
        } else if (event.getSubject().getId() == null || event.getStart_time() == null || event.getEnd_time() == null || event.getLocalization() == null || event.getDay() == null) {
            return true;
        }
        event.setDay(event.getDay().toLowerCase());
        return checkDay(event.getDay());
    }

    public static boolean checkDay(String day) {
        switch (day) {
            case "monday":
            case "tuesday":
            case "wednesday":
            case "thursday":
            case "friday":
                return false;
        }
        return true;
    }

    public static boolean checkGrade(Grade grade) {
        return (grade == null || grade.getSubject() == null || grade.getSubject().getId() == null || grade.getDate() == null || grade.getDescription() == null);
    }

    public static boolean checkFullSubject(Subject subject) {
        return (subject.getId() == null || subject.getName() == null);
    }

    public static boolean checkFullEvent(Event event) {
        return (event.getId() == null || event.getDay() == null
                || checkFullSubject(event.getSubject()) || event.getLocalization() == null
                || event.getStart_time() == null || event.getEnd_time() == null);
    }

    public static boolean checkFullGrade(Grade grade) {
        return (grade.getId() == null || grade.getDescription() == null
                || grade.getDate() == null || checkFullSubject(grade.getSubject()));
    }
}
