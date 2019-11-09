package com.wolfram.timetable.database.repositories;

import com.wolfram.timetable.database.entities.Grade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Integer> {

    @Query(value = "SELECT * FROM Grade g WHERE g.user_id = :userId", nativeQuery = true)
    List<Grade> getGradesFromUser(@Param("userId") Integer userId);
}
