package com.theatomicity.scheduler.backend.tasktemplate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class StreamGobbler implements Runnable {

    private final InputStream inputStream;
    private final Consumer<String> consumer;

    public StreamGobbler(final InputStream inputStream, final Consumer<String> consumer) {
        this.inputStream = inputStream;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        new BufferedReader(new InputStreamReader(this.inputStream)).lines()
                .forEach(this.consumer);
    }
}
