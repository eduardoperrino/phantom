package io.nammok.phantom.core.event;

import io.nammok.phantom.core.domain.Circular;

public class CircularCreatedEvent extends CircularEvent {
    public CircularCreatedEvent(Circular body) {
        this.body = body;
    }
}
