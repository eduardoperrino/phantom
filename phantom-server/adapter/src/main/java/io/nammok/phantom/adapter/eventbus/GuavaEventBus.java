package io.nammok.phantom.adapter.eventbus;

import com.google.common.eventbus.EventBus;
import io.nammok.phantom.core.event.EventListener;
import io.nammok.phantom.core.event.PhantomEventBus;
import org.slf4j.Logger;

public class GuavaEventBus implements PhantomEventBus {

    private final Logger logger;
    private final EventBus eventBus;

    public GuavaEventBus(Logger logger) {
        this.logger = logger;
        this.eventBus = new EventBus();
    }

    public void register(EventListener listener) {
        eventBus.register(listener);
    }

    @Override
    public void post(Object event) {
        eventBus.post(event);
    }
}
