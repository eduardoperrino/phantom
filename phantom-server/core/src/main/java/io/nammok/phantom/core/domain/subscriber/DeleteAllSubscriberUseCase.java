package io.nammok.phantom.core.domain.subscriber;

import io.nammok.phantom.core.port.SubscriberRepository;
import io.nammok.phantom.core.usecase.UseCase;
import lombok.Builder;
import lombok.Value;

public class DeleteAllSubscriberUseCase extends UseCase<DeleteAllSubscriberUseCase.InputValues, DeleteAllSubscriberUseCase.OutputValues> {

    private SubscriberRepository repository;

    public DeleteAllSubscriberUseCase(SubscriberRepository repository) { this.repository = repository; }

    @Override
    public DeleteAllSubscriberUseCase.OutputValues execute(DeleteAllSubscriberUseCase.InputValues input) {
        repository.deleteAll();
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
