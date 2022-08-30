package io.nammok.phantom.core.usecase.circular;

import io.nammok.phantom.core.event.CircularDeletedEvent;
import io.nammok.phantom.core.event.PhantomEventBus;
import io.nammok.phantom.core.port.CircularRepository;
import io.nammok.phantom.core.usecase.UseCase;
import lombok.Builder;
import lombok.Value;

public class DeleteAllCircularUseCase extends UseCase<DeleteAllCircularUseCase.InputValues, DeleteAllCircularUseCase.OutputValues> {
    private CircularRepository repository;
    private PhantomEventBus eventBus;

    public DeleteAllCircularUseCase(CircularRepository repository,
                                    PhantomEventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public OutputValues execute(InputValues input) {
        repository.deleteAll().forEach(circular -> eventBus.post(new CircularDeletedEvent(circular)));
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
