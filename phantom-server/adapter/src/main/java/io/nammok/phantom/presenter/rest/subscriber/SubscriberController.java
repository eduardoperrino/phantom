package io.nammok.phantom.presenter.rest.subscriber;

import io.nammok.phantom.core.domain.Identity;
import io.nammok.phantom.core.usecase.UseCaseExecutor;
import io.nammok.phantom.core.usecase.subscriber.*;
import io.nammok.phantom.presenter.rest.entity.SubscriberRequest;
import io.nammok.phantom.presenter.rest.entity.SubscriberResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class SubscriberController implements SubscriberResource {
    private final UseCaseExecutor useCaseExecutor;
    private final SubscriberMapper subscriberMapper;
    private final CreateSubscriberUseCase createSubscriberUseCase;
    private final ReadAllSubscriberUseCase readAllSubscriberUseCase;
    private final ReadSubscriberUseCase readSubscriberUseCase;
    private final DeleteAllSubscriberUseCase deleteAllSubscriberUseCase;
    private final DeleteSubscriberUseCase deleteSubscriberUseCase;

    public SubscriberController(UseCaseExecutor useCaseExecutor,
                                SubscriberMapper subscriberMapper,
                                CreateSubscriberUseCase createSubscriberUseCase,
                                ReadAllSubscriberUseCase readAllSubscriberUseCase,
                                ReadSubscriberUseCase readSubscriberUseCase,
                                DeleteAllSubscriberUseCase deleteAllSubscriberUseCase,
                                DeleteSubscriberUseCase deleteSubscriberUseCase) {
        this.useCaseExecutor = useCaseExecutor;
        this.subscriberMapper = subscriberMapper;
        this.createSubscriberUseCase = createSubscriberUseCase;
        this.readAllSubscriberUseCase = readAllSubscriberUseCase;
        this.readSubscriberUseCase = readSubscriberUseCase;
        this.deleteAllSubscriberUseCase = deleteAllSubscriberUseCase;
        this.deleteSubscriberUseCase = deleteSubscriberUseCase;
    }

    @Override
    public CompletableFuture<SubscriberResponse> create(SubscriberRequest request) {
        return useCaseExecutor.execute(
                createSubscriberUseCase,
                CreateSubscriberUseCase.InputValues.builder()
                        .withName(request.getName())
                        .withEmail(request.getEmail())
                        .build(),
                (output) -> subscriberMapper.convertEntityToResponse(output.getSubscriber())
        );
    }

    @Override
    public CompletableFuture<List<SubscriberResponse>> readAll() {
        return useCaseExecutor.execute(
                readAllSubscriberUseCase,
                null,
                (output) -> output.getSubscribers().stream()
                        .map(subscriberMapper::convertEntityToResponse)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public CompletableFuture<SubscriberResponse> readByIdentity(String id) {
        return useCaseExecutor.execute(
                readSubscriberUseCase,
                ReadSubscriberUseCase.InputValues.builder()
                        .withIdentity(Identity.of(id))
                        .build(),
                (output) -> subscriberMapper.convertEntityToResponse(output.getSubscriber())
        );
    }

    @Override
    public CompletableFuture<Void> deleteAll() {
        return useCaseExecutor.execute(
                deleteAllSubscriberUseCase,
                null,
                (output) -> null
        );
    }

    @Override
    public CompletableFuture<Void> deleteByIdentity(String id) {
        return useCaseExecutor.execute(
                deleteSubscriberUseCase,
                DeleteSubscriberUseCase.InputValues.builder()
                        .withIdentity(Identity.of(id))
                        .build(),
                (output) -> null
        );
    }
}
