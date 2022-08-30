package io.nammok.phantom.presenter.realtime.emitter;

import org.slf4j.Logger;
import java.io.IOException;

public class SseNotificationService implements NotificationService {

    private Logger logger;
    private EmitterRepository emitterRepository;
    private EventMapper eventMapper;

    public SseNotificationService(Logger logger,
                                  EmitterRepository emitterRepository,
                                  EventMapper eventMapper) {
        this.logger = logger;
        this.emitterRepository = emitterRepository;
        this.eventMapper = eventMapper;
    }

    @Override
    public void broadcastNotification(EventDto event) {
        emitterRepository.getAllMembers().forEach(memberId -> {
            doSendNotification(memberId, event);
        });
    }

    private void doSendNotification(String memberId, EventDto event) {
        this.emitterRepository.get(memberId).ifPresentOrElse(sseEmitter -> {
            try {
                logger.debug("Sending event: {} for member: {}", event, memberId);
                sseEmitter.send(eventMapper.toSseEventBuilder(event));
            } catch (IOException | IllegalStateException e) {
                logger.debug("Error while sending event: {} for member: {} - exception: {}", event, memberId, e);
                emitterRepository.remove(memberId);
            }
        }, () -> logger.debug("No emitter for member {}", memberId));
    }
}
