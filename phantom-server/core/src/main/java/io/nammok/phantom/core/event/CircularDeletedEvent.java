package io.nammok.phantom.core.event;

import io.nammok.phantom.core.domain.Circular;

public class CircularDeletedEvent extends CircularEvent {
    public CircularDeletedEvent(Circular body) {
        this.body = body;
    }
}
