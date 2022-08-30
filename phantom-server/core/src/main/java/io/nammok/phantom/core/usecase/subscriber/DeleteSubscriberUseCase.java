package io.nammok.phantom.core.usecase.subscriber;

import io.nammok.phantom.core.domain.Identity;
import io.nammok.phantom.core.domain.NotFoundException;
import io.nammok.phantom.core.event.PhantomEventBus;
import io.nammok.phantom.core.event.SubscriberDeletedEvent;
import io.nammok.phantom.core.port.SubscriberRepository;
import io.nammok.phantom.core.usecase.UseCase;
import lombok.Builder;
import lombok.Value;

public class DeleteSubscriberUseCase extends UseCase<DeleteSubscriberUseCase.InputValues, DeleteSubscriberUseCase.OutputValues> {

    private SubscriberRepository repository;
    private final PhantomEventBus eventBus;

    public DeleteSubscriberUseCase(SubscriberRepository repository, PhantomEventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public DeleteSubscriberUseCase.OutputValues execute(DeleteSubscriberUseCase.InputValues input) {
        Identity identity = input.getIdentity();

        return repository.deleteByIdentity(identity).map(subscriber -> {
                    eventBus.post(new SubscriberDeletedEvent(subscriber));
                    return subscriber;
                })
                .map(e -> DeleteSubscriberUseCase.OutputValues.builder().build())
                .orElseThrow(() -> NotFoundException.of(identity.getId()));
    }

    @Value
    @Builder(setterPrefix = "with")
    public static class InputValues implements UseCase.InputValues {
        Identity identity;
    }

    @Value
    @Builder(setterPrefix = "with")
    public static class OutputValues implements UseCase.OutputValues {
    }
}
