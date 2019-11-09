package com.wolfram.timetable.database.repositories;

import com.wolfram.timetable.database.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
