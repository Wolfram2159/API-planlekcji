package com.wolfram.timetable.database.repositories;

import com.wolfram.timetable.database.entities.Event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<Event, Integer> {
    @Query(value = "SELECT * FROM Event e WHERE e.id = :id", nativeQuery = true)
    Event getEvent(@Param("id") Long id);
}
