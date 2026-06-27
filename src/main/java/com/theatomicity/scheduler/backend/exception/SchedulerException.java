package com.theatomicity.scheduler.backend.exception;

public class SchedulerException extends RuntimeException {

    public SchedulerException(final String executionNotFound) {
        super(executionNotFound);
    }
    
}
