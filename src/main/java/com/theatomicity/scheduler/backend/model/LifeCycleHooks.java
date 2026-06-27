package com.theatomicity.scheduler.backend.model;

public interface LifeCycleHooks {
    void onCreate();

    void onStart();

    void onCompletionError();

    void onCompletionOk();

    void onCancel();
}
