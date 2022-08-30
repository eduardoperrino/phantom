package io.nammok.phantom.presenter.realtime.emitter;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.slf4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class SseNotificationServiceTest {

    public static final String MEMBER_ID = UUID.randomUUID().toString();

    @Mock
    Logger logger;
    @Mock
    EmitterRepository emitterRepository;
    @Mock
    EventMapper eventMapper;
    @Captor
    ArgumentCaptor<SseEmitter.SseEventBuilder> sseEventBuilderCaptor;

    @InjectMocks
    SseNotificationService sseNotificationService;

    @Test
    void shouldBroadcastEvent() throws IOException {
        // Background
        SseEmitter emitterSpy = spy(new SseEmitter());
        when(emitterRepository.getAllMembers()).thenReturn(List.of(MEMBER_ID));
        when(emitterRepository.get(MEMBER_ID)).thenReturn(Optional.of(emitterSpy));
        when(eventMapper.toSseEventBuilder(generateEventDto())).thenReturn(generateSseEventBuilder());

        // When
        sseNotificationService.broadcastNotification(generateEventDto());

        // Then
        verify(emitterSpy).send(sseEventBuilderCaptor.capture());
        assertThat(sseEventBuilderCaptor.getValue()).isNotNull();
    }


    @Test
    void shouldDoNothingOnMissingEmitters () {
        // Background
        when(emitterRepository.getAllMembers()).thenReturn(List.of());

        // When
        sseNotificationService.broadcastNotification(generateEventDto());

        // Then
        verifyNoInteractions(eventMapper);
    }

    private EventDto generateEventDto() {
        return EventDto.builder().withType(EventDto.EventType.MESSAGE).withBody("Body content").build();
    }

    private SseEmitter.SseEventBuilder generateSseEventBuilder() {
        return SseEmitter.event()
                .id(UUID.randomUUID().toString())
                .name(EventDto.EventType.MESSAGE.getType())
                .data("Body Content");
    }

}