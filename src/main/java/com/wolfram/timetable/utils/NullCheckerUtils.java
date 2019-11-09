package com.wolfram.timetable.utils;

import com.wolfram.timetable.database.entities.Event;
import com.wolfram.timetable.database.entities.Grade;
import com.wolfram.timetable.database.entities.Subject;

public class NullCheckerUtils {
    private NullCheckerUtils() {
    }

    public static boolean checkSubject(Subject subject) {
        return subject.getName() == null;
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
}
