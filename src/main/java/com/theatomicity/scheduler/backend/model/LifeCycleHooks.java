package com.theatomicity.scheduler.backend.model;

public interface LifeCycleHooks {
    void onCreate();

    void onStart();

    void onError();

    void onCompletionOk();

    void onCompletionKo();

    void onCancel();
}
