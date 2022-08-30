package io.nammok.phantom.config;

import io.nammok.phantom.adapter.eventbus.GuavaEventBus;
import io.nammok.phantom.adapter.eventbus.GuavaEventListener;
import io.nammok.phantom.core.event.PhantomEventBus;
import io.nammok.phantom.presenter.realtime.emitter.NotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;

@Configuration
public class EventBusConfig {

    @Bean
    public PhantomEventBus phantomEventBus(Logger logger, NotificationService notificationService) {
        GuavaEventBus guavaEventBus = new GuavaEventBus(logger);
        guavaEventBus.register(new GuavaEventListener(logger, notificationService));
        return guavaEventBus;
    }
}
