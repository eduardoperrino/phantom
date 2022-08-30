package io.nammok.phantom.core.domain.subscriber;

import io.nammok.phantom.core.domain.Subscriber;
import io.nammok.phantom.core.port.SubscriberRepository;
import io.nammok.phantom.core.usecase.UseCase;
import lombok.Builder;
import lombok.Value;

import java.util.List;

public class ReadAllSubscriberUseCase extends UseCase<ReadAllSubscriberUseCase.InputValues, ReadAllSubscriberUseCase.OutputValues> {

    private SubscriberRepository repository;

    public ReadAllSubscriberUseCase(SubscriberRepository repository) { this.repository = repository; }

    @Override
    public ReadAllSubscriberUseCase.OutputValues execute(ReadAllSubscriberUseCase.InputValues input) {
        return ReadAllSubscriberUseCase.OutputValues.builder()
                .withSubscribers(repository.readAll())
                .build();
    }

    @Value
    @Builder(builderClassName = "Builder", setterPrefix = "with")
    public static class InputValues implements UseCase.InputValues {
    }

    @Value
    @Builder(builderClassName = "Builder", setterPrefix = "with")
    public static class OutputValues implements UseCase.OutputValues {
        List<Subscriber> subscribers;
    }
}
