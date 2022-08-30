package io.nammok.phantom.presenter.realtime.emitter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class InMemoryEmitterRepositoryTest {

    public static final String MEMBER_ID = "MEMBER_ID";

    @Mock
    Logger logger;

    @InjectMocks
    InMemoryEmitterRepository inMemoryEmitterRepository;

    @Test
    void shouldAddNewEmitter() {
        assertThat(inMemoryEmitterRepository.get(MEMBER_ID)).isEmpty();

        SseEmitter emitter = new SseEmitter();
        inMemoryEmitterRepository.addOrReplaceEmitter(MEMBER_ID, emitter);

        Optional<SseEmitter> emitterAfterAddOptional = inMemoryEmitterRepository.get(MEMBER_ID);
        assertThat(emitterAfterAddOptional).isNotEmpty();
        assertThat(emitterAfterAddOptional.get()).isEqualTo(emitter);
    }

    @Test
    void shouldReplaceExistingEmitter() {
        SseEmitter firstEmitter = new SseEmitter();
        inMemoryEmitterRepository.addOrReplaceEmitter(MEMBER_ID, firstEmitter);

        SseEmitter secondEmitter = new SseEmitter();
        inMemoryEmitterRepository.addOrReplaceEmitter(MEMBER_ID, secondEmitter);

        Optional<SseEmitter> emitterAfterReplaceOptional = inMemoryEmitterRepository.get(MEMBER_ID);
        assertThat(emitterAfterReplaceOptional).isNotEmpty();
        assertThat(emitterAfterReplaceOptional.get()).isEqualTo(secondEmitter);
    }

    @Test
    void shouldRemoveExistingEmitter() {
        inMemoryEmitterRepository.addOrReplaceEmitter(MEMBER_ID, new SseEmitter());
        assertThat(inMemoryEmitterRepository.get(MEMBER_ID)).isNotEmpty();

        inMemoryEmitterRepository.remove(MEMBER_ID);
        assertThat(inMemoryEmitterRepository.get(MEMBER_ID)).isEmpty();
    }

    @Test
    void shouldDoNothingOnRemoveNonExistingEmitter() {
        assertThat(inMemoryEmitterRepository.get(MEMBER_ID)).isEmpty();
        inMemoryEmitterRepository.remove(MEMBER_ID);
        assertThat(inMemoryEmitterRepository.get(MEMBER_ID)).isEmpty();
    }
}