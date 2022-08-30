package io.nammok.phantom.core.domain.subscriber;

import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import io.nammok.phantom.core.domain.Identity;
import io.nammok.phantom.core.domain.NotFoundException;
import io.nammok.phantom.core.domain.Subscriber;
import io.nammok.phantom.core.port.SubscriberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@ExtendWith(RandomBeansExtension.class)
class DeleteSubscriberUseCaseTest {

    @Mock
    SubscriberRepository repository;

    @InjectMocks
    DeleteSubscriberUseCase cut;

    DeleteSubscriberUseCase.InputValues.InputValuesBuilder inputBuilder;
    DeleteSubscriberUseCase.OutputValues.OutputValuesBuilder outputBuilder;

    @BeforeEach
    void setUp() {
        inputBuilder = DeleteSubscriberUseCase.InputValues.builder();
        outputBuilder = DeleteSubscriberUseCase.OutputValues.builder();
    }

    @Test
    void delete(@Random Subscriber deleted) {
        // Background
        when(repository.deleteByIdentity(deleted.getId())).thenReturn(Optional.of(deleted));

        // Given
        DeleteSubscriberUseCase.InputValues input = inputBuilder
                .withIdentity(deleted.getId())
                .build();

        // When
        DeleteSubscriberUseCase.OutputValues actual = cut.execute(input);

        // Then
        verify(repository, times(1)).deleteByIdentity(deleted.getId());

        DeleteSubscriberUseCase.OutputValues expected = outputBuilder.build();

        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    void failToDelete(@Random Identity toDelete) {
        // Background
        when(repository.deleteByIdentity(toDelete)).thenReturn(Optional.empty());

        // Given
        DeleteSubscriberUseCase.InputValues input = inputBuilder
                .withIdentity(toDelete)
                .build();

        // When
        NotFoundException actual = assertThrows(NotFoundException.class, () -> {
            cut.execute(input);
        });

        // Then
        verify(repository, times(1)).deleteByIdentity(toDelete);

        NotFoundException expected = NotFoundException.of(toDelete.getId());

        assertThat(actual).isNotNull().isEqualToComparingFieldByFieldRecursively(expected);
    }

    @Nested
    class Input {
        @Test
        void nullObject() {
            // Given

            // When
            DeleteSubscriberUseCase.InputValues actual = inputBuilder.build();

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
            DeleteSubscriberUseCase.InputValues actual = inputBuilder.build();

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
            DeleteSubscriberUseCase.OutputValues actual = outputBuilder.build();

            // Then
            assertThat(actual).isNotNull();
        }
    }

}