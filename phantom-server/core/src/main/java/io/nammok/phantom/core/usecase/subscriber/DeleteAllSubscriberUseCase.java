package io.nammok.phantom.core.usecase.subscriber;

import io.nammok.phantom.core.event.PhantomEventBus;
import io.nammok.phantom.core.event.SubscriberDeletedEvent;
import io.nammok.phantom.core.port.SubscriberRepository;
import io.nammok.phantom.core.usecase.UseCase;
import lombok.Builder;
import lombok.Value;

public class DeleteAllSubscriberUseCase extends UseCase<DeleteAllSubscriberUseCase.InputValues, DeleteAllSubscriberUseCase.OutputValues> {

    private final SubscriberRepository repository;
    private final PhantomEventBus eventBus;

    public DeleteAllSubscriberUseCase(SubscriberRepository repository, PhantomEventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public DeleteAllSubscriberUseCase.OutputValues execute(DeleteAllSubscriberUseCase.InputValues input) {
        repository.deleteAll().forEach(subscriber -> eventBus.post(new SubscriberDeletedEvent(subscriber)));
        return DeleteAllSubscriberUseCase.OutputValues.builder().build();
    }

    @Value
    @Builder(builderClassName = "Builder", setterPrefix = "with")
    public static class InputValues implements UseCase.InputValues {
    }

    @Value
    @Builder(builderClassName = "Builder", setterPrefix = "with")
    public static class OutputValues implements UseCase.OutputValues {
    }
}
