package com.wolfram.timetable.database.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
    private Date start_time;
    private Date end_time;
    private String localization;
    private String day;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event() {

    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", subject_id=" + subject +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", localization='" + localization + '\'' +
                ", day='" + day + '\'' +
                '}';
    }

    /*public String getTimeString(){
        String startTime = Utils.getTimeString(getStart_time());
        String endTime = Utils.getTimeString(getEnd_time());
        return startTime + " - " + endTime;
    }*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
