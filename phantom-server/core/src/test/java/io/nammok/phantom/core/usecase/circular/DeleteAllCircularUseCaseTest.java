package io.nammok.phantom.core.usecase.circular;

import io.nammok.phantom.core.domain.Circular;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import io.nammok.phantom.core.event.CircularDeletedEvent;
import io.nammok.phantom.core.event.PhantomEventBus;
import io.nammok.phantom.core.port.CircularRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(RandomBeansExtension.class)
class DeleteAllCircularUseCaseTest {

    @Mock
    CircularRepository repository;
    @Mock
    PhantomEventBus eventBus;
    @Captor
    ArgumentCaptor<CircularDeletedEvent> eventArgumentCaptor;

    @InjectMocks
    DeleteAllCircularUseCase cut;

    DeleteAllCircularUseCase.InputValues.Builder inputBuilder;
    DeleteAllCircularUseCase.OutputValues.Builder outputBuilder;

    @BeforeEach
    void setUp() {
        inputBuilder = DeleteAllCircularUseCase.InputValues.builder();
        outputBuilder = DeleteAllCircularUseCase.OutputValues.builder();
    }

    @Test
    void deleteAllEmptyRepository() {
        // Background
        when(repository.deleteAll()).thenReturn(Collections.emptyList());

        // Given
        DeleteAllCircularUseCase.InputValues input = inputBuilder.build();

        // When
        DeleteAllCircularUseCase.OutputValues actual = cut.execute(input);

        // Then
        verify(repository, times(1)).deleteAll();

        verify(eventBus, times(0)).post(eventArgumentCaptor.capture());

        DeleteAllCircularUseCase.OutputValues expected = outputBuilder.build();

        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    void deleteAllNonEmptyRepository(@Random(size = 5, type = Circular.class) List<Circular> deleted) {
        // Background
        when(repository.deleteAll()).thenReturn(deleted);

        // Given
        DeleteAllCircularUseCase.InputValues input = inputBuilder.build();

        // When
        DeleteAllCircularUseCase.OutputValues actual = cut.execute(input);

        // Then
        verify(repository, times(1)).deleteAll();

        verify(eventBus, times(5)).post(eventArgumentCaptor.capture());

        DeleteAllCircularUseCase.OutputValues expected = outputBuilder.build();

        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Nested
    class Input {
        @Test
        void nullObject() {
            // Given

            // When
            DeleteAllCircularUseCase.InputValues actual = inputBuilder.build();

            // Then
            assertThat(actual).isNotNull();
        }
    }

    @Nested
    class Output {
        @Test
        void nullObject() {
            // Given

            // When
            DeleteAllCircularUseCase.OutputValues actual = outputBuilder.build();

            // Then
            assertThat(actual).isNotNull();
        }
    }
}
