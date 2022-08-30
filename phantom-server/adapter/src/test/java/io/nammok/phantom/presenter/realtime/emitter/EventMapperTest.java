package io.nammok.phantom.presenter.realtime.emitter;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static org.assertj.core.api.Assertions.assertThat;

class EventMapperTest {

    EventMapper eventMapper = new EventMapper();

    @Test
    void shouldMapToSseEventBuilder() {
        SseEmitter.SseEventBuilder sseEventBuilder = this.eventMapper.toSseEventBuilder(
                EventDto.builder().withType(EventDto.EventType.MESSAGE).withBody("Body content").build());

        assertThat(sseEventBuilder).isNotNull();
    }

}