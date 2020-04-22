package com.wolfram.timetable.controllers;

import com.wolfram.timetable.database.entities.Event;
import com.wolfram.timetable.database.entities.Subject;
import com.wolfram.timetable.database.entities.User;
import com.wolfram.timetable.database.repositories.EventRepository;
import com.wolfram.timetable.database.repositories.SubjectRepository;
import com.wolfram.timetable.utils.JWTUtils;
import com.wolfram.timetable.utils.JsonCreator;
import com.wolfram.timetable.utils.Responses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class EventController {
    private final EventRepository eventRepository;
    private final SubjectRepository subjectRepository;
    private final JsonCreator jsonCreator;

    @Autowired
    public EventController(JsonCreator jsonCreator, SubjectRepository subjectRepository, EventRepository eventRepository) {
        this.jsonCreator = jsonCreator;
        this.subjectRepository = subjectRepository;
        this.eventRepository = eventRepository;
    }

    @PostMapping(value = "/subject/{id}/event")
    public ResponseEntity<String> createEvent(@CookieValue("jwt") String authorization, @PathVariable("id") Integer subjectId, @RequestBody Event event) {
        if (event.checkIfNotHaveNecessaryFields()) {
            return new ResponseEntity<>(Responses.UNPROCESSABLE_ENTITY, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Integer userId = JWTUtils.getUserId(authorization);
        List<Subject> subjectsFromUser = subjectRepository.getSubjectsFromUser(userId);
        if (checkIfNotListContainsSubjectId(subjectsFromUser, subjectId)) {
            return new ResponseEntity<>(Responses.FORBIDDEN, HttpStatus.FORBIDDEN);
        }
        event.setSubject(new Subject(subjectId));
        event.setUser(new User(userId));
        event.setId(null);
        Event save = eventRepository.save(event);
        String json = jsonCreator.createJsonForObject(save);
        return new ResponseEntity<>(json, HttpStatus.CREATED);
    }

    private boolean checkIfNotListContainsSubjectId(List<Subject> subjectList, Integer subjectId) {
        for (Subject subject : subjectList) {
            if (subject.getId().equals(subjectId)) {
                return false;
            }
        }
        return true;
    }

    @GetMapping(value = "/event")
    public ResponseEntity<String> getEventsFromDay(@CookieValue("jwt") String authorization, @RequestParam(name = "day") String day) {
        Integer userId = JWTUtils.getUserId(authorization);
        Event event = new Event();
        event.setDay(day);
        if (!event.checkDayFormatIsCorrect()) {
            return new ResponseEntity<>(Responses.UNPROCESSABLE_ENTITY, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        List<Event> eventsFromDay = eventRepository.getEventsFromDay(userId, day);
        String json = jsonCreator.createJsonForObject(eventsFromDay);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @GetMapping(value = "/event/{id}")
    public ResponseEntity<String> getEvent(@CookieValue("jwt") String authorization, @PathVariable("id") Integer eventId) {
        Integer userId = JWTUtils.getUserId(authorization);
        Event eventFromUser = eventRepository.getEvent(eventId);
        if (!userId.equals(eventFromUser.getUser().getId())) {
            return new ResponseEntity<>(Responses.FORBIDDEN, HttpStatus.FORBIDDEN);
        }
        String json = jsonCreator.createJsonForObject(eventFromUser);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @DeleteMapping(value = "/event/{id}")
    public ResponseEntity<String> deleteEvent(@CookieValue("jwt") String authorization, @PathVariable("id") Integer eventId) {
        Integer userId = JWTUtils.getUserId(authorization);
        List<Event> eventsFromUser = eventRepository.getEventsFromUser(userId);
        if (checkIfNotListContainsEvent(eventsFromUser, eventId)) {
            return new ResponseEntity<>(Responses.FORBIDDEN, HttpStatus.FORBIDDEN);
        }
        Event eventToDelete = new Event();
        eventToDelete.setId(eventId);
        eventRepository.delete(eventToDelete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/event/{id}")
    public ResponseEntity<String> updateEvent(@CookieValue("jwt") String authorization, @PathVariable("id") Integer eventId, @RequestBody Event event) {
        if (event.checkIfNotHaveNecessaryFields()) {
            return new ResponseEntity<>(Responses.UNPROCESSABLE_ENTITY, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Integer userId = JWTUtils.getUserId(authorization);
        List<Event> eventsFromUser = eventRepository.getEventsFromUser(userId);
        if (checkIfNotListContainsEvent(eventsFromUser, eventId)) {
            return new ResponseEntity<>(Responses.FORBIDDEN, HttpStatus.FORBIDDEN);
        }
        event.setUser(new User(userId));
        event.setId(eventId);
        Event save = eventRepository.save(event);
        String json = jsonCreator.createJsonForObject(save);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    private boolean checkIfNotListContainsEvent(List<Event> eventsFromUser, Integer searchingEventId) {
        for (Event event : eventsFromUser) {
            if (Objects.equals(event.getId(), searchingEventId)) {
                return false;
            }
        }
        return true;
    }
}
