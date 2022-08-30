package io.nammok.phantom.core.domain.subscriber;

import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import io.nammok.phantom.core.domain.Subscriber;

import io.nammok.phantom.core.port.SubscriberRepository;
import io.nammok.phantom.core.usecase.identity.GenerateRandomIdentityUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(RandomBeansExtension.class)
class CreateSubscriberUseCaseTest {

    @Mock
    SubscriberRepository repository;
    @Mock
    GenerateRandomIdentityUseCase generateRandomIdentityUseCase;

    @InjectMocks
    CreateSubscriberUseCase cut;

    CreateSubscriberUseCase.InputValues.Builder inputBuilder;
    CreateSubscriberUseCase.OutputValues.Builder outputBuilder;

    @BeforeEach
    void setUp() {
        inputBuilder = CreateSubscriberUseCase.InputValues.builder();
        outputBuilder = CreateSubscriberUseCase.OutputValues.builder();
    }

    @Test
    void create(@Random Subscriber created) {
        // Background
        when(repository.create(Subscriber.builder()
                .withId(created.getId())
                .withName(created.getName())
                .withEmail(created.getEmail())
                .build())).thenReturn(created);

        GenerateRandomIdentityUseCase.OutputValues randomIdentity = GenerateRandomIdentityUseCase.OutputValues.builder()
                .withIdentity(created.getId())
                .build();
        when(generateRandomIdentityUseCase.execute(GenerateRandomIdentityUseCase.InputValues.builder().build()))
                .thenReturn(randomIdentity);

        ArgumentCaptor<Subscriber> repoCapture = ArgumentCaptor.forClass(Subscriber.class);

        // Given
        CreateSubscriberUseCase.InputValues input = inputBuilder
                .withName(created.getName())
                .withEmail(created.getEmail())
                .build();

        // When
        CreateSubscriberUseCase.OutputValues actual = cut.execute(input);

        // Then
        verify(repository).create(repoCapture.capture());
        assertThat(repoCapture.getValue()).isNotNull().satisfies(captureParam -> {
            assertThat(captureParam.getId()).isEqualTo(created.getId());
        });

        CreateSubscriberUseCase.OutputValues expected = outputBuilder
                .withSubscriber(created)
                .build();

        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Nested
    class Input {
        @Test
        void nullObject() {
            // Given

            // When
            CreateSubscriberUseCase.InputValues actual = inputBuilder.build();

            // Then
            assertThat(actual).isNotNull().satisfies(inputValues -> {
                assertThat(inputValues.getName()).isNull();
                assertThat(inputValues.getEmail()).isNull();
            });
        }

        @Test
        void fullObject(@Random Subscriber toCreate) {
            // Given
            inputBuilder
                    .withName(toCreate.getName())
                    .withEmail(toCreate.getEmail());

            // When
            CreateSubscriberUseCase.InputValues actual = inputBuilder.build();

            // Then
            assertThat(actual).isNotNull().satisfies(inputValues -> {
                assertThat(inputValues.getName()).isEqualTo(toCreate.getName());
                assertThat(inputValues.getEmail()).isEqualTo(toCreate.getEmail());
            });
        }
    }

    @Nested
    class Output {
        @Test
        void nullObject() {
            // Given

            // When
            CreateSubscriberUseCase.OutputValues actual = outputBuilder.build();

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
            CreateSubscriberUseCase.OutputValues actual = outputBuilder.build();

            // Then
            assertThat(actual).isNotNull().satisfies(outputValues -> assertThat(outputValues.getSubscriber()).isEqualTo(created));
        }
    }

}