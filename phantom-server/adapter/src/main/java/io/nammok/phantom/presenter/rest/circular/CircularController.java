package io.nammok.phantom.presenter.rest.circular;

import io.nammok.phantom.core.domain.Identity;
import io.nammok.phantom.core.usecase.UseCaseExecutor;
import io.nammok.phantom.core.usecase.circular.*;
import io.nammok.phantom.presenter.rest.entity.CircularRequest;
import io.nammok.phantom.presenter.rest.entity.CircularResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class CircularController implements CircularResource {
    private final UseCaseExecutor useCaseExecutor;
    private final CircularMapper circularMapper;
    private final CreateCircularUseCase createCircularUseCase;
    private final ReadAllCircularUseCase readAllCircularUseCase;
    private final ReadCircularUseCase readCircularUseCase;
    private final DeleteAllCircularUseCase deleteAllCircularUseCase;
    private final DeleteCircularUseCase deleteCircularUseCase;

    public CircularController(UseCaseExecutor useCaseExecutor,
                              CircularMapper circularMapper,
                              CreateCircularUseCase createCircularUseCase,
                              ReadAllCircularUseCase readAllCircularUseCase,
                              ReadCircularUseCase readCircularUseCase,
                              DeleteAllCircularUseCase deleteAllCircularUseCase,
                              DeleteCircularUseCase deleteCircularUseCase) {
        this.useCaseExecutor = useCaseExecutor;
        this.circularMapper = circularMapper;
        this.createCircularUseCase = createCircularUseCase;
        this.readAllCircularUseCase = readAllCircularUseCase;
        this.readCircularUseCase = readCircularUseCase;
        this.deleteAllCircularUseCase = deleteAllCircularUseCase;
        this.deleteCircularUseCase = deleteCircularUseCase;
    }

    @Override
    public CompletableFuture<CircularResponse> create(CircularRequest request) {
        return useCaseExecutor.execute(
                createCircularUseCase,
                CreateCircularUseCase.InputValues.builder()
                        .withName(request.getName())
                        .withDescription(request.getDescription())
                        .build(),
                (output) -> circularMapper.convertEntityToResponse(output.getCircular())
        );
    }

    @Override
    public CompletableFuture<List<CircularResponse>> readAll() {
        return useCaseExecutor.execute(
                readAllCircularUseCase,
                null,
                (output) -> output.getCircular().stream()
                        .map(circularMapper::convertEntityToResponse)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public CompletableFuture<CircularResponse> readByIdentity(String id) {
        return useCaseExecutor.execute(
                readCircularUseCase,
                ReadCircularUseCase.InputValues.builder()
                        .withIdentity(Identity.of(id))
                        .build(),
                (output) -> circularMapper.convertEntityToResponse(output.getCircular())
        );
    }

    @Override
    public CompletableFuture<Void> deleteAll() {
        return useCaseExecutor.execute(
                deleteAllCircularUseCase,
                null,
                (output) -> null
        );
    }

    @Override
    public CompletableFuture<Void> deleteByIdentity(String id) {
        return useCaseExecutor.execute(
                deleteCircularUseCase,
                DeleteCircularUseCase.InputValues.builder()
                        .withIdentity(Identity.of(id))
                        .build(),
                (output) -> null
        );
    }
}
