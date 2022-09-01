package io.nammok.phantom.core.event;

import io.nammok.phantom.core.domain.Circular;
import lombok.NonNull;

public class CircularCreatedEvent extends CircularEvent {

    public CircularCreatedEvent(@NonNull Circular body) {
        super(body);
    }
}
