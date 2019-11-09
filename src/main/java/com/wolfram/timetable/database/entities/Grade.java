package com.wolfram.timetable.database.entities;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Grade {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
