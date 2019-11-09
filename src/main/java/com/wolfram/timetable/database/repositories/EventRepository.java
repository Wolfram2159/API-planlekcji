package com.wolfram.timetable.database.repositories;

import com.wolfram.timetable.database.entities.Event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    @Query(value = "SELECT * FROM Event e WHERE e.user_id = :userId AND e.day = :day", nativeQuery = true)
    List<Event> getEventsFromDay(@Param("userId") Integer userId, @Param("day") String day);

    @Query(value = "SELECT * FROM Event e WHERE e.user_id = :userId", nativeQuery = true)
    List<Event> getEventsFromUser(@Param("userId") Integer userId);
}
