package io.nammok.phantom.core.domain.subscriber;

import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import io.nammok.phantom.core.domain.Subscriber;
import io.nammok.phantom.core.port.SubscriberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(RandomBeansExtension.class)
class ReadAllSubscriberUseCaseTest {

    @Mock
    SubscriberRepository repository;

    @InjectMocks
    ReadAllSubscriberUseCase cut;

    ReadAllSubscriberUseCase.InputValues.Builder inputBuilder;
    ReadAllSubscriberUseCase.OutputValues.Builder outputBuilder;

    @BeforeEach
    void setUp() {
        inputBuilder = ReadAllSubscriberUseCase.InputValues.builder();
        outputBuilder = ReadAllSubscriberUseCase.OutputValues.builder();
    }

    @Test
    void readAllEmptyRepository() {
        // Background

        // Given
        ReadAllSubscriberUseCase.InputValues input = inputBuilder.build();

        // When
        ReadAllSubscriberUseCase.OutputValues actual = cut.execute(input);

        // Then
        verify(repository, times(1)).readAll();

        ReadAllSubscriberUseCase.OutputValues expected = outputBuilder
                .withSubscribers(Collections.emptyList())
                .build();

        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    void readAllNonEmptyRepository(@Random(size = 5, type = Subscriber.class) List<Subscriber> toRead) {
        // Background
        when(repository.readAll()).thenReturn(toRead);

        // Given
        ReadAllSubscriberUseCase.InputValues input = inputBuilder.build();

        // When
        ReadAllSubscriberUseCase.OutputValues actual = cut.execute(input);

        // Then
        verify(repository, times(1)).readAll();

        ReadAllSubscriberUseCase.OutputValues expected = outputBuilder
                .withSubscribers(toRead)
                .build();

        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Nested
    class Input {
        @Test
        void nullObject() {
            // Given

            // When
            ReadAllSubscriberUseCase.InputValues actual = inputBuilder.build();

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
            ReadAllSubscriberUseCase.OutputValues actual = outputBuilder.build();

            // Then
            assertThat(actual).isNotNull().satisfies(outputValues -> {
                assertThat(outputValues.getSubscribers()).isNull();
            });
        }

        @Test
        void fullObject(@Random(size = 5, type = Subscriber.class) List<Subscriber> read) {
            // Given
            outputBuilder
                    .withSubscribers(read);

            // When
            ReadAllSubscriberUseCase.OutputValues actual = outputBuilder.build();

            // Then
            assertThat(actual).isNotNull().satisfies(outputValues -> assertThat(outputValues.getSubscribers()).isEqualTo(read));
        }
    }

}