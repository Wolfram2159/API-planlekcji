package com.wolfram.timetable.controllers;

import com.wolfram.timetable.database.entities.Event;
import com.wolfram.timetable.database.entities.User;
import com.wolfram.timetable.database.repositories.EventRepository;
import com.wolfram.timetable.utils.JWTUtils;
import com.wolfram.timetable.utils.JsonCreator;
import com.wolfram.timetable.utils.NullCheckerUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller
public class EventController {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private JsonCreator jsonCreator;


    @PostMapping(value = "/event")
    public ResponseEntity<String> createEvent(@RequestHeader String authorization, @RequestBody Event event){
        Integer userId = JWTUtils.getUserId(authorization);
        if (NullCheckerUtils.checkEvent(event)){
            return new ResponseEntity<>("Event without necessary fields.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        event.setUser(new User(userId));
        Event savedEvent = eventRepository.save(event);
        String json = jsonCreator.createJsonForObject(savedEvent);
        return new ResponseEntity<>(json, HttpStatus.CREATED);
    }

    @GetMapping(value = "/event")
    public ResponseEntity<String> getEventsFromDay(@RequestHeader String authorization, @RequestParam(name = "day") String day){
        Integer userId = JWTUtils.getUserId(authorization);
        day = day.toLowerCase();
        if (NullCheckerUtils.checkDay(day)) {
            return new ResponseEntity<>("Wrong day, only accepted days are : monday, tuesday, wednesday, thursday, friday. Case sensitive have no matters.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        List<Event> eventsFromDay = eventRepository.getEventsFromDay(userId, day);
        String json = jsonCreator.createJsonForObject(eventsFromDay);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @DeleteMapping(value = "/event/{id}")
    public ResponseEntity<String> deleteEvent(@RequestHeader String authorization, @PathVariable Integer id){
        Integer userId = JWTUtils.getUserId(authorization);
        if (id == null) {
            return new ResponseEntity<>("No event id.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        List<Event> eventsFromUser = eventRepository.getEventsFromUser(userId);

        if (!checkIfListContainsEvent(eventsFromUser, id)){
            return new ResponseEntity<>("You have no permissions for this record.", HttpStatus.FORBIDDEN);
        }

        Event eventToDelete = new Event();
        eventToDelete.setId(id);
        eventRepository.delete(eventToDelete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private boolean checkIfListContainsEvent(List<Event> eventsFromUser, Integer searchingEventId){
        for (Event event : eventsFromUser) {
            if (Objects.equals(event.getId(), searchingEventId)){
                return true;
            }
        }
        return false;
    }
}
