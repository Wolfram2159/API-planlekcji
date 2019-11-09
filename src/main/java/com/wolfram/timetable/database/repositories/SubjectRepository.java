package com.wolfram.timetable.database.repositories;

import com.wolfram.timetable.database.entities.Subject;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
}
