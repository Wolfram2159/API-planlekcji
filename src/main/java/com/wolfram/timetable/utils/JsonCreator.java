package com.wolfram.timetable.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonCreator {

    private ObjectMapper objectMapper;

    public JsonCreator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String createJsonForObject(Object object){
        String s = "";
        try {
            s = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;
    }
}
