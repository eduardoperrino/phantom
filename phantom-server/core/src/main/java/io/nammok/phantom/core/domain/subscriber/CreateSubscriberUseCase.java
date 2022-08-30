package io.nammok.phantom.core.domain.subscriber;

import io.nammok.phantom.core.domain.Identity;
import io.nammok.phantom.core.domain.Subscriber;
import io.nammok.phantom.core.port.SubscriberRepository;
import io.nammok.phantom.core.usecase.UseCase;
import io.nammok.phantom.core.usecase.identity.GenerateRandomIdentityUseCase;
import lombok.Builder;
import lombok.Value;

public class CreateSubscriberUseCase extends UseCase<CreateSubscriberUseCase.InputValues, CreateSubscriberUseCase.OutputValues> {

    private SubscriberRepository repository;
    private GenerateRandomIdentityUseCase generateRandomIdentityUseCase;

    public CreateSubscriberUseCase(SubscriberRepository repository, GenerateRandomIdentityUseCase generateRandomIdentityUseCase) {
        this.repository = repository;
        this.generateRandomIdentityUseCase = generateRandomIdentityUseCase;
    }

    @Override
    public CreateSubscriberUseCase.OutputValues execute(CreateSubscriberUseCase.InputValues input) {
        Identity identity = generateRandomIdentityUseCase.execute(GenerateRandomIdentityUseCase.InputValues.builder().build()).getIdentity();

        Subscriber subscriber = Subscriber.builder()
                .withId(identity)
                .withName(input.getName())
                .withEmail(input.getEmail())
                .build();

        return CreateSubscriberUseCase.OutputValues.builder()
                .withSubscriber(repository.create(subscriber))
                .build();
    }

    @Value
    @Builder(builderClassName = "Builder", setterPrefix = "with")
    public static class InputValues implements UseCase.InputValues {
        String name;
        String email;
    }

    @Value
    @Builder(builderClassName = "Builder", setterPrefix = "with")
    public static class OutputValues implements UseCase.OutputValues {
        Subscriber subscriber;
    }
}
