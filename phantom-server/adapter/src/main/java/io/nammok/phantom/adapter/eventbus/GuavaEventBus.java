package io.nammok.phantom.adapter.eventbus;

import com.google.common.eventbus.EventBus;
import io.nammok.phantom.core.event.CircularEvent;
import io.nammok.phantom.core.event.EventListener;
import io.nammok.phantom.core.event.PhantomEventBus;
import org.slf4j.Logger;

public class GuavaEventBus implements PhantomEventBus {

    private Logger logger;
    private EventBus eventBus;

    public GuavaEventBus(Logger logger) {
        this.logger = logger;
        this.eventBus = new EventBus();
    }

    public void register(EventListener listener) {
        this.eventBus.register(listener);
    }

    @Override
    public void post(CircularEvent event) {
        this.eventBus.post(event);
    }
}
