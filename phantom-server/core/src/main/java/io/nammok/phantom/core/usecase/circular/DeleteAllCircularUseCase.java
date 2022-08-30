package io.nammok.phantom.core.usecase.circular;

import io.nammok.phantom.core.port.CircularRepository;
import io.nammok.phantom.core.usecase.UseCase;
import lombok.Builder;
import lombok.Value;

public class DeleteAllCircularUseCase extends UseCase<DeleteAllCircularUseCase.InputValues, DeleteAllCircularUseCase.OutputValues> {
    private CircularRepository repository;

    public DeleteAllCircularUseCase(CircularRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        repository.deleteAll();
        return OutputValues.builder().build();
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
