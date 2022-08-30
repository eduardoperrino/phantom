package io.nammok.phantom.core.domain.subscriber;

import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import io.nammok.phantom.core.domain.Identity;
import io.nammok.phantom.core.domain.NotFoundException;
import io.nammok.phantom.core.domain.Subscriber;
import io.nammok.phantom.core.port.SubscriberRepository;
import io.nammok.phantom.core.usecase.subscriber.ReadSubscriberUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(RandomBeansExtension.class)
class ReadSubscriberUseCaseTest {

    @Mock
    SubscriberRepository repository;

    @InjectMocks
    ReadSubscriberUseCase cut;

    ReadSubscriberUseCase.InputValues.InputValuesBuilder inputBuilder;
    ReadSubscriberUseCase.OutputValues.OutputValuesBuilder outputBuilder;

    @BeforeEach
    void setUp() {
        inputBuilder = ReadSubscriberUseCase.InputValues.builder();
        outputBuilder = ReadSubscriberUseCase.OutputValues.builder();
    }

    @Test
    void read(@Random Subscriber read) {
        // Background
        when(repository.readByIdentity(read.getId())).thenReturn(Optional.of(read));

        // Given
        ReadSubscriberUseCase.InputValues input = inputBuilder
                .withIdentity(read.getId())
                .build();

        // When
        ReadSubscriberUseCase.OutputValues actual = cut.execute(input);

        // Then
        verify(repository, times(1)).readByIdentity(read.getId());

        ReadSubscriberUseCase.OutputValues expected = outputBuilder.withSubscriber(read).build();

        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    void failToRead(@Random Identity toRead) {
        // Background

        // Given
        ReadSubscriberUseCase.InputValues input = inputBuilder
                .withIdentity(toRead)
                .build();

        // When
        NotFoundException actual = assertThrows(NotFoundException.class, () -> {
            cut.execute(input);
        });

        // Then
        verify(repository, times(1)).readByIdentity(toRead);

        NotFoundException expected = NotFoundException.of(toRead.getId());

        assertThat(actual).isNotNull().isEqualToComparingFieldByFieldRecursively(expected);
    }

    @Nested
    class Input {
        @Test
        void nullObject() {
            // Given

            // When
            ReadSubscriberUseCase.InputValues actual = inputBuilder.build();

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
            ReadSubscriberUseCase.InputValues actual = inputBuilder.build();

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
            ReadSubscriberUseCase.OutputValues actual = outputBuilder.build();

            // Then
            assertThat(actual).isNotNull().satisfies(outputValues -> {
                assertThat(outputValues.getSubscriber()).isNull();
            });
        }

        @Test
        void fullObject(@Random Subscriber created) {
            // Given
            outputBuilder
                    .withSubscriber(created);

            // When
            ReadSubscriberUseCase.OutputValues actual = outputBuilder.build();

            // Then
            assertThat(actual).isNotNull().satisfies(outputValues -> assertThat(outputValues.getSubscriber()).isEqualTo(created));
        }
    }

}