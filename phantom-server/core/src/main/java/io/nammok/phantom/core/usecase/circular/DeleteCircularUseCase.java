package io.nammok.phantom.core.usecase.circular;

import io.nammok.phantom.core.domain.Identity;
import io.nammok.phantom.core.domain.NotFoundException;
import io.nammok.phantom.core.event.CircularDeletedEvent;
import io.nammok.phantom.core.event.PhantomEventBus;
import io.nammok.phantom.core.port.CircularRepository;
import io.nammok.phantom.core.usecase.UseCase;
import lombok.Builder;
import lombok.Value;

import java.util.Optional;

public class DeleteCircularUseCase extends UseCase<DeleteCircularUseCase.InputValues, DeleteCircularUseCase.OutputValues> {
    private final CircularRepository repository;
    private final PhantomEventBus eventBus;

    public DeleteCircularUseCase(CircularRepository repository, PhantomEventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public OutputValues execute(InputValues input) {
        Identity identity = input.getIdentity();

        return repository.deleteByIdentity(identity)
                .map( circular -> {
                    eventBus.post(new CircularDeletedEvent(circular));
                    return circular;
                })
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
