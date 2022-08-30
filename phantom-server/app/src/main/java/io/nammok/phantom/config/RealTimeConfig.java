package io.nammok.phantom.config;

import io.nammok.phantom.presenter.realtime.emitter.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;

@Configuration
public class RealTimeConfig {

    @Bean
    public EventMapper eventMapper() {
        return new EventMapper();
    }

    @Bean
    public EmitterRepository emitterRepository(Logger logger) {
        return new InMemoryEmitterRepository(logger);
    }

    @Bean
    public EmitterService emitterService(Logger logger, EmitterRepository emitterRepository) {
        return new EmitterService(logger, 180000L, emitterRepository);
    }

    @Bean
    public NotificationService notificationService(Logger logger, EmitterRepository emitterRepository,
                                                   EventMapper eventMapper) {
        return new SseNotificationService(logger, emitterRepository, eventMapper);
    }
}
