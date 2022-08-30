package io.nammok.phantom.adapter.eventbus;

import com.google.common.eventbus.Subscribe;
import io.nammok.phantom.core.event.CircularCreatedEvent;
import io.nammok.phantom.core.event.EventListener;
import io.nammok.phantom.presenter.realtime.emitter.EventDto;
import io.nammok.phantom.presenter.realtime.emitter.NotificationService;
import org.slf4j.Logger;

public class GuavaEventListener implements EventListener<CircularCreatedEvent> {
    private final Logger logger;
    private final NotificationService notificationService;

    public GuavaEventListener(Logger logger, NotificationService notificationService) {
        this.logger = logger;
        this.notificationService = notificationService;
    }

    @Override
    @Subscribe
    public  void onEvent(CircularCreatedEvent event) {
        logger.info("Event: {}", event.getBody().getDescription());
        notificationService.broadcastNotification(
                EventDto.builder().withType(EventDto.EventType.MESSAGE).withBody(event.getBody().getDescription()).build());
    }
}
