package io.nammok.phantom.presenter.realtime.emitter;

import org.slf4j.Logger;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryEmitterRepository implements EmitterRepository {

    private Logger logger;

    public InMemoryEmitterRepository(Logger logger) {
        this.logger = logger;
    }

    private Map<String, SseEmitter> userEmitterMap = new ConcurrentHashMap<>();

    @Override
    public void addOrReplaceEmitter(String memberId, SseEmitter emitter) {
        userEmitterMap.put(memberId, emitter);
    }

    @Override
    public void remove(String memberId) {
        if (userEmitterMap != null && userEmitterMap.containsKey(memberId)) {
            logger.debug("Removing emitter for member: {}", memberId);
            userEmitterMap.remove(memberId);
        } else {
            logger.debug("No emitter to remove for member: {}", memberId);
        }
    }

    @Override
    public Optional<SseEmitter> get(String memberId) {
        return Optional.ofNullable(userEmitterMap.get(memberId));
    }

    @Override
    public List<String> getAllMembers() {return List.copyOf(this.userEmitterMap.keySet());
    }


}
