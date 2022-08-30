package io.nammok.phantom.presenter.realtime;

import io.nammok.phantom.presenter.realtime.emitter.EmitterService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/events")
@CrossOrigin
public class EventController {

    private Logger logger;
    private final EmitterService emitterService;

    public EventController(Logger logger, EmitterService emitterService) {
        this.logger = logger;
        this.emitterService = emitterService;
    }

    @GetMapping
    public SseEmitter subscribeToEvents() {
        return emitterService.createEmitter(UUID.randomUUID().toString());
    }

}
