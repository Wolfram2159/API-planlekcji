package com.wolfram.timetable.database.repositories;

import com.wolfram.timetable.database.entities.Subject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    @Query(value = "SELECT * FROM Subject s WHERE s.user_id = :userId", nativeQuery = true)
    List<Subject> getSubjectsFromUser(@Param("userId") Integer userId);

    @Query(value = "SELECT * FROM Subject s WHERE s.id = :subjectId", nativeQuery = true)
    Subject getSubject(@Param("subjectId") Integer subjectId);
}
