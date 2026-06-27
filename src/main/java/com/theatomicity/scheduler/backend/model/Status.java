package com.theatomicity.scheduler.backend.model;

public enum Status {
    CREATED, RUNNING, COMPLETED, ERROR, CANCELLED;

    public Boolean isNotCompleted() {
        return Boolean.TRUE.equals(RUNNING.equals(this) || CREATED.equals(this));
    }
}
