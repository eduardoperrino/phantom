package io.nammok.phantom.core.event;

import io.nammok.phantom.core.domain.Subscriber;

public class SubscriberDeletedEvent extends SubscriberEvent {
    public SubscriberDeletedEvent(Subscriber body) {
        super(body);
    }
}
