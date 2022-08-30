package io.nammok.phantom.core.usecase.circular;

import io.nammok.phantom.core.domain.Circular;
import io.nammok.phantom.core.domain.Identity;
import io.nammok.phantom.core.event.CircularCreatedEvent;
import io.nammok.phantom.core.event.PhantomEventBus;
import io.nammok.phantom.core.port.CircularRepository;
import io.nammok.phantom.core.usecase.UseCase;
import io.nammok.phantom.core.usecase.identity.GenerateRandomIdentityUseCase;
import lombok.Builder;
import lombok.Value;

public class CreateCircularUseCase extends UseCase<CreateCircularUseCase.InputValues, CreateCircularUseCase.OutputValues> {
    private CircularRepository repository;
    private GenerateRandomIdentityUseCase generateRandomIdentityUseCase;
    private PhantomEventBus phantomEventBus;

    public CreateCircularUseCase(CircularRepository repository,
                                 GenerateRandomIdentityUseCase generateRandomIdentityUseCase,
                                 PhantomEventBus phantomEventBus) {
        this.repository = repository;
        this.generateRandomIdentityUseCase = generateRandomIdentityUseCase;
        this.phantomEventBus = phantomEventBus;
    }

    @Override
    public OutputValues execute(InputValues input) {
        Identity identity = generateRandomIdentityUseCase.execute(GenerateRandomIdentityUseCase.InputValues.builder().build()).getIdentity();

        Circular circular = Circular.builder()
                .withId(identity)
                .withName(input.getName())
                .withDescription(input.getDescription())
                .build();

        OutputValues outputValues =  OutputValues.builder()
                .withCircular(repository.create(circular))
                .build();
        this.phantomEventBus.post(new CircularCreatedEvent(circular));
        return outputValues;
    }

    @Value
    @Builder(builderClassName = "Builder", setterPrefix = "with")
    public static class InputValues implements UseCase.InputValues {
        String name;
        String description;
    }

    @Value
    @Builder(builderClassName = "Builder", setterPrefix = "with")
    public static class OutputValues implements UseCase.OutputValues {
        Circular circular;
    }
}
