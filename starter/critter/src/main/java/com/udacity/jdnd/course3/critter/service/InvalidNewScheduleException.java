package com.udacity.jdnd.course3.critter.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Schedule cannot be created with given input.")
public class InvalidNewScheduleException extends RuntimeException{
    public InvalidNewScheduleException() {
    }

    public InvalidNewScheduleException(String message) {
        super(message);
    }
}
