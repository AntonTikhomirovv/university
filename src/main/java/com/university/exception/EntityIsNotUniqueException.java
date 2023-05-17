package com.university.exception;

import org.springframework.dao.DataAccessException;

public class EntityIsNotUniqueException extends DataAccessException {
    public EntityIsNotUniqueException(String message) {
        super(message);
    }

    public EntityIsNotUniqueException(String message, Throwable cause) {
        super(message, cause);
    }
}
