package io.nammok.phantom.core.event;

import io.nammok.phantom.core.domain.Subscriber;

import java.io.Serializable;
import java.time.Instant;

public class SubscriberEvent implements Serializable {

    protected Subscriber body;
    protected Long createdDateMs = Instant.EPOCH.toEpochMilli();

    public Subscriber getBody() {
        return this.body;
    }

    public Long getCreatedDateMs() {
        return createdDateMs;
    }
}
