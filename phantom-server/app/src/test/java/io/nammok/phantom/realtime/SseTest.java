package io.nammok.phantom.realtime;

import io.nammok.phantom.PhantomServerApplication;
import io.nammok.phantom.presenter.realtime.emitter.EventDto;
import io.nammok.phantom.presenter.rest.entity.CircularRequest;
import io.nammok.phantom.realtime.utils.AsyncEventClient;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = PhantomServerApplication.class)

public class SseTest {

    private AsyncEventClient asyncEventClient;
    private final static CircularRequest circularRequest = CircularRequest.builder()
            .withName("Post microchip")
            .withDescription("Post Try to override the ADP pixel, maybe it will compress the neural matrix!").build();

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void setup() {
        this.asyncEventClient = new AsyncEventClient(port);
    }
    @Test
    void shouldReceiveEvent() throws InterruptedException {
        var eventStream = asyncEventClient.getEvents();

        List<EventDto> receivedEvents = new ArrayList<>();
        var disposable = eventStream.subscribe(
                event -> receivedEvents.add(new EventDto(EventDto.EventType.MESSAGE, event.data())),
                error -> {throw new AssertionError("Event stream error");});

        waitForSubscription();
        publishCircular();

        Awaitility.await()
                .pollDelay(3, TimeUnit.SECONDS)
                .until(() -> receivedEvents.size() == 1);
        EventDto receivedEvent = receivedEvents.get(0);
        assertThat(receivedEvent.getBody()).isEqualTo(circularRequest.getDescription());
        disposable.dispose();
    }

    private void publishCircular() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        testRestTemplate.exchange("/circular", HttpMethod.POST, new HttpEntity<>(circularRequest, headers), Void.class);
    }

    private void waitForSubscription() throws InterruptedException {
        Thread.sleep(200);
    }
}
