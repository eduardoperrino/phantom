package io.nammok.phantom.core.event;

import io.nammok.phantom.core.domain.Circular;
import lombok.*;

public class CircularDeletedEvent extends CircularEvent {
    public CircularDeletedEvent(@NonNull Circular body) {
        super(body);
    }
}
