package io.nammok.phantom.realtime.utils;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class AsyncEventClient {

    private final WebClient webClient;

    private final ParameterizedTypeReference<ServerSentEvent<String>> type
            = new ParameterizedTypeReference<>() {};

    public AsyncEventClient(int serverPort) {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:" + serverPort)
                .build();
    }

    public Flux<ServerSentEvent<String>> getEvents() {
        var requestSpec = webClient.get()
                .uri("/events");

        return requestSpec.retrieve().bodyToFlux(type);
    }


}
