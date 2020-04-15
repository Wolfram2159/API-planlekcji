package com.wolfram.timetable.custom;

import com.wolfram.timetable.database.entities.Grade;
import com.wolfram.timetable.database.entities.Subject;

import java.util.List;

public class SubjectWithGrades {
    private Subject subject;
    private List<Grade> gradeList;

    public SubjectWithGrades(Subject subject, List<Grade> gradeList) {
        this.subject = subject;
        this.gradeList = gradeList;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public List<Grade> getGradeList() {
        return gradeList;
    }

    public void setGradeList(List<Grade> gradeList) {
        this.gradeList = gradeList;
    }
}
