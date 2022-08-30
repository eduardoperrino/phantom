package io.nammok.phantom.presenter.rest.subscriber;

import io.nammok.phantom.presenter.rest.entity.SubscriberRequest;
import io.nammok.phantom.presenter.rest.entity.SubscriberResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/subscriber")
public interface SubscriberResource {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CompletableFuture<SubscriberResponse> create(@RequestBody SubscriberRequest request);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    CompletableFuture<List<SubscriberResponse>> readAll();

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    CompletableFuture<SubscriberResponse> readByIdentity(@PathVariable("id") String id);

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    CompletableFuture<Void> deleteAll();

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    CompletableFuture<Void> deleteByIdentity(@PathVariable("id") String id);
}
