package com.wolfram.timetable.database.repositories;

import com.wolfram.timetable.database.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM User u", nativeQuery = true)
    List<User> getAllUsers();

    @Query(value = "SELECT * FROM User u WHERE u.username = :username AND u.password = :password", nativeQuery = true)
    User getUser(@Param("username") String username, @Param("password") String password);
}
