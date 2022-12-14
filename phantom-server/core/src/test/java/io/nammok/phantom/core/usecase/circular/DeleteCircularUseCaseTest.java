package io.nammok.phantom.core.usecase.circular;

import io.nammok.phantom.core.domain.Circular;
import io.nammok.phantom.core.domain.Identity;
import io.nammok.phantom.core.domain.NotFoundException;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(RandomBeansExtension.class)
class DeleteCircularUseCaseTest {

    @Mock
    CircularRepository repository;
    @Mock
    PhantomEventBus eventBus;
    @Captor
    ArgumentCaptor<CircularDeletedEvent> eventCaptor;


    @InjectMocks
    DeleteCircularUseCase cut;

    DeleteCircularUseCase.InputValues.InputValuesBuilder inputBuilder;
    DeleteCircularUseCase.OutputValues.OutputValuesBuilder outputBuilder;

    @BeforeEach
    void setUp() {
        inputBuilder = DeleteCircularUseCase.InputValues.builder();
        outputBuilder = DeleteCircularUseCase.OutputValues.builder();
    }

    @Test
    void delete(@Random Circular deleted) {
        // Background
        when(repository.deleteByIdentity(deleted.getId())).thenReturn(Optional.of(deleted));

        // Given
        DeleteCircularUseCase.InputValues input = inputBuilder
                .withIdentity(deleted.getId())
                .build();

        // When
        DeleteCircularUseCase.OutputValues actual = cut.execute(input);

        // Then
        verify(repository, times(1)).deleteByIdentity(deleted.getId());

        verify(eventBus, times(1)).post(eventCaptor.capture());

        DeleteCircularUseCase.OutputValues expected = outputBuilder.build();

        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    void failToDelete(@Random Identity toDelete) {
        // Background
        when(repository.deleteByIdentity(toDelete)).thenReturn(Optional.empty());

        // Given
        DeleteCircularUseCase.InputValues input = inputBuilder
                .withIdentity(toDelete)
                .build();

        // When
        NotFoundException actual = assertThrows(NotFoundException.class, () -> {
            cut.execute(input);
        });

        // Then
        verify(repository, times(1)).deleteByIdentity(toDelete);

        verify(eventBus, times(0)).post(eventCaptor.capture());

        NotFoundException expected = NotFoundException.of(toDelete.getId());

        assertThat(actual).isNotNull().isEqualToComparingFieldByFieldRecursively(expected);
    }

    @Nested
    class Input {
        @Test
        void nullObject() {
            // Given

            // When
            DeleteCircularUseCase.InputValues actual = inputBuilder.build();

            // Then
            assertThat(actual).isNotNull().satisfies(inputValues -> {
                assertThat(inputValues.getIdentity()).isNull();
            });
        }

        @Test
        void fullObject(@Random Identity toDelete) {
            // Given
            inputBuilder
                    .withIdentity(toDelete);

            // When
            DeleteCircularUseCase.InputValues actual = inputBuilder.build();

            // Then
            assertThat(actual).isNotNull().satisfies(inputValues -> {
                assertThat(inputValues.getIdentity()).isEqualTo(toDelete);
            });
        }
    }

    @Nested
    class Output {
        @Test
        void nullObject() {
            // Given

            // When
            DeleteCircularUseCase.OutputValues actual = outputBuilder.build();

            // Then
            assertThat(actual).isNotNull();
        }
    }
}
