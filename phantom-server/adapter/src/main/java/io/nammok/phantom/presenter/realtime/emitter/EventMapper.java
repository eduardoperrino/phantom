package io.nammok.phantom.presenter.realtime.emitter;

import org.slf4j.Logger;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

public class EventMapper {

    public SseEmitter.SseEventBuilder toSseEventBuilder(EventDto event) {
        return SseEmitter.event()
                .id(UUID.randomUUID().toString())
                .name(event.getType().getType())
                .data(event.getBody());
    }
}
