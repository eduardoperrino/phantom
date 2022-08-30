package io.nammok.phantom.presenter.realtime.emitter;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmitterServiceTest {

    @Mock
    private Logger logger;
    @Mock
    private EmitterRepository emitterRepository;

    private EmitterService emitterService;

    @BeforeEach
    void setUp() { emitterService = new EmitterService(logger, 20, emitterRepository); }

    @Test
    void shouldReturnNewEmitter() {
        // Given
        var memberId = UUID.randomUUID().toString();
        var emitter = emitterService.createEmitter(memberId);

        // When
        assertThat(emitter).isNotNull();

        // Then
        verify(emitterRepository).addOrReplaceEmitter(eq(memberId), eq(emitter));
    }

}