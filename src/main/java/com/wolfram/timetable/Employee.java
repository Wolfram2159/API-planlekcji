package com.wolfram.timetable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Employee {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    private String dept;

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", dept=" + dept + "]";
    }

}
