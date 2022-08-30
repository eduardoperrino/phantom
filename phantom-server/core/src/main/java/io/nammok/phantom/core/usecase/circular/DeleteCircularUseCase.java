package io.nammok.phantom.core.usecase.circular;

import io.nammok.phantom.core.domain.Identity;
import io.nammok.phantom.core.domain.NotFoundException;
import io.nammok.phantom.core.port.CircularRepository;
import io.nammok.phantom.core.usecase.UseCase;
import lombok.Builder;
import lombok.Value;

public class DeleteCircularUseCase extends UseCase<DeleteCircularUseCase.InputValues, DeleteCircularUseCase.OutputValues> {
    private CircularRepository repository;

    public DeleteCircularUseCase(CircularRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        Identity identity = input.getIdentity();

        return repository.deleteByIdentity(identity)
                .map(e -> OutputValues.builder().build())
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
