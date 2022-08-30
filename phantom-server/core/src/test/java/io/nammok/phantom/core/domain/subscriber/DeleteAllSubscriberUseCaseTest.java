package io.nammok.phantom.core.domain.subscriber;

import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import io.nammok.phantom.core.domain.Subscriber;
import io.nammok.phantom.core.event.PhantomEventBus;
import io.nammok.phantom.core.event.SubscriberDeletedEvent;
import io.nammok.phantom.core.port.SubscriberRepository;
import io.nammok.phantom.core.usecase.subscriber.DeleteAllSubscriberUseCase;
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
class DeleteAllSubscriberUseCaseTest {

    @Mock
    SubscriberRepository repository;
    @Mock
    PhantomEventBus eventBus;
    @Captor
    ArgumentCaptor<SubscriberDeletedEvent> eventArgumentCaptor;

    @InjectMocks
    DeleteAllSubscriberUseCase cut;

    DeleteAllSubscriberUseCase.InputValues.Builder inputBuilder;
    DeleteAllSubscriberUseCase.OutputValues.Builder outputBuilder;

    @BeforeEach
    void setUp() {
        inputBuilder = DeleteAllSubscriberUseCase.InputValues.builder();
        outputBuilder = DeleteAllSubscriberUseCase.OutputValues.builder();
    }

    @Test
    void deleteAllEmptyRepository() {
        // Background
        when(repository.deleteAll()).thenReturn(Collections.emptyList());

        // Given
        DeleteAllSubscriberUseCase.InputValues input = inputBuilder.build();

        // When
        DeleteAllSubscriberUseCase.OutputValues actual = cut.execute(input);

        // Then
        verify(repository, times(1)).deleteAll();

        verify(eventBus, times(0)).post(eventArgumentCaptor.capture());

        DeleteAllSubscriberUseCase.OutputValues expected = outputBuilder.build();

        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    void deleteAllNonEmptyRepository(@Random(size = 5, type = Subscriber.class) List<Subscriber> deleted) {
        // Background
        when(repository.deleteAll()).thenReturn(deleted);

        // Given
        DeleteAllSubscriberUseCase.InputValues input = inputBuilder.build();

        // When
        DeleteAllSubscriberUseCase.OutputValues actual = cut.execute(input);

        // Then
        verify(repository, times(1)).deleteAll();

        verify(eventBus, times(5)).post(eventArgumentCaptor.capture());

        DeleteAllSubscriberUseCase.OutputValues expected = outputBuilder.build();

        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Nested
    class Input {
        @Test
        void nullObject() {
            // Given

            // When
            DeleteAllSubscriberUseCase.InputValues actual = inputBuilder.build();

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
            DeleteAllSubscriberUseCase.OutputValues actual = outputBuilder.build();

            // Then
            assertThat(actual).isNotNull();
        }
    }

}