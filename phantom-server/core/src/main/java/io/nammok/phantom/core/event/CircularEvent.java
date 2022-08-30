package io.nammok.phantom.core.event;


import io.nammok.phantom.core.domain.Circular;

import java.time.Instant;

public abstract class CircularEvent {
    protected Circular body;
    protected Long createdDateMs = Instant.EPOCH.toEpochMilli();

    public Circular getBody() {
        return this.body;
    }

    public Long getCreatedDateMs() {
        return this.createdDateMs;
    }

}
