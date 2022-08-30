package io.nammok.phantom.presenter.realtime.emitter;

import org.slf4j.Logger;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class EmitterService {

    private Logger logger;
    private final long eventsTimeout;
    private final EmitterRepository repository;

    public EmitterService(Logger logger,
                          long eventsTimeout,
                          EmitterRepository repository) {
        this.logger = logger;
        this.eventsTimeout = eventsTimeout;
        this.repository = repository;
    }

    public SseEmitter createEmitter(String memberId) {
        SseEmitter emitter = new SseEmitter(eventsTimeout);
        emitter.onCompletion(() -> repository.remove(memberId));
        emitter.onTimeout(() -> repository.remove(memberId));
        emitter.onError(e -> {
            logger.error("Create SseEmitter exception", e);
            repository.remove(memberId);
        });
        repository.addOrReplaceEmitter(memberId, emitter);
        return emitter;
    }

}
