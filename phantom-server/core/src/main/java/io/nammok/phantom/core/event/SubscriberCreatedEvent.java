package io.nammok.phantom.core.event;

import io.nammok.phantom.core.domain.Subscriber;

public class SubscriberCreatedEvent extends SubscriberEvent {

    public SubscriberCreatedEvent(Subscriber body) { this.body = body;}
}
