package io.nammok.phantom.core.domain.subscriber;

import io.nammok.phantom.core.domain.Identity;
import io.nammok.phantom.core.domain.NotFoundException;
import io.nammok.phantom.core.port.SubscriberRepository;
import io.nammok.phantom.core.usecase.UseCase;
import lombok.Builder;
import lombok.Value;

public class DeleteSubscriberUseCase extends UseCase<DeleteSubscriberUseCase.InputValues, DeleteSubscriberUseCase.OutputValues> {

    private SubscriberRepository repository;

    public DeleteSubscriberUseCase(SubscriberRepository repository) { this.repository = repository; }

    @Override
    public DeleteSubscriberUseCase.OutputValues execute(DeleteSubscriberUseCase.InputValues input) {
        Identity identity = input.getIdentity();

        return repository.deleteByIdentity(identity)
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
