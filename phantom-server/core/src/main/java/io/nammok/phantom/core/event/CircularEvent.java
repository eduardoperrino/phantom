package io.nammok.phantom.core.event;


import io.nammok.phantom.core.domain.Circular;

import java.io.Serializable;
import java.time.Instant;

public abstract class CircularEvent implements Serializable {
    protected Circular body;
    protected Long createdDateMs = Instant.EPOCH.toEpochMilli();

    public Circular getBody() {
        return body;
    }

    public Long getCreatedDateMs() {
        return createdDateMs;
    }

}
