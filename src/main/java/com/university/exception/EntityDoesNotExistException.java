package com.university.exception;

import org.springframework.dao.DataAccessException;

public class EntityDoesNotExistException extends DataAccessException {
    public EntityDoesNotExistException(String message) {
        super(message);
    }

    public EntityDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
