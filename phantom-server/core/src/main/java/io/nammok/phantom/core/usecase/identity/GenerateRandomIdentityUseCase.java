package io.nammok.phantom.core.usecase.identity;

import io.nammok.phantom.core.domain.Identity;
import io.nammok.phantom.core.usecase.UseCase;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

public class GenerateRandomIdentityUseCase extends UseCase<GenerateRandomIdentityUseCase.InputValues, GenerateRandomIdentityUseCase.OutputValues> {
    @Override
    public OutputValues execute(InputValues input) {
        return OutputValues.builder()
                .withIdentity(Identity.of(UUID.randomUUID().toString()))
                .build();
    }

    @Value
    @Builder(builderClassName = "Builder", setterPrefix = "with")
    public static class InputValues implements UseCase.InputValues {
    }

    @Value
    @Builder(builderClassName = "Builder", setterPrefix = "with")
    public static class OutputValues implements UseCase.OutputValues {
        Identity identity;
    }
}
