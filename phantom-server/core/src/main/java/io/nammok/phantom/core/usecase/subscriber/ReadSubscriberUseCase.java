package io.nammok.phantom.core.usecase.subscriber;

import io.nammok.phantom.core.domain.Identity;
import io.nammok.phantom.core.domain.NotFoundException;
import io.nammok.phantom.core.domain.Subscriber;
import io.nammok.phantom.core.port.SubscriberRepository;
import io.nammok.phantom.core.usecase.UseCase;
import lombok.Builder;
import lombok.Value;


public class ReadSubscriberUseCase extends UseCase<ReadSubscriberUseCase.InputValues, ReadSubscriberUseCase.OutputValues> {

    private SubscriberRepository repository;

    public ReadSubscriberUseCase(SubscriberRepository repository) { this.repository = repository; }

    @Override
    public ReadSubscriberUseCase.OutputValues execute(ReadSubscriberUseCase.InputValues input) {
        Identity identity = input.getIdentity();

        return repository.readByIdentity(identity)
                .map(e -> ReadSubscriberUseCase.OutputValues.builder().withSubscriber(e).build())
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
        Subscriber subscriber;
    }
}
