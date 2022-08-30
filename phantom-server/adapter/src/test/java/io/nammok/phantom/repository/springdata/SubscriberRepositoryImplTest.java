package io.nammok.phantom.repository.springdata;

import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import io.nammok.phantom.core.domain.Identity;
import io.nammok.phantom.core.domain.Subscriber;
import io.nammok.phantom.repository.springdata.entity.SubscriberEntity;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;

@ExtendWith(RandomBeansExtension.class)
@ExtendWith(MockitoExtension.class)
class SubscriberRepositoryImplTest {

    @Mock
    Logger logger;
    @Mock
    SubscriberEntityRepository subscriberEntityRepository;

    @InjectMocks
    SubscriberRepositoryImpl cut;

    @Nested
    class Create {
        @Test
        void create(@Random SubscriberEntity existent) {
            // Background
            when(subscriberEntityRepository.save(existent)).thenReturn(existent);

            // Given
            Subscriber toCreate = Subscriber.builder()
                    .withId(Identity.of(existent.getId()))
                    .withName(existent.getName())
                    .withEmail(existent.getEmail())
                    .build();

            // When
            Subscriber actual = cut.create(toCreate);

            // Then
            Subscriber expected = Subscriber.builder()
                    .withId(Identity.of(existent.getId()))
                    .withName(existent.getName())
                    .withEmail(existent.getEmail())
                    .build();

            assertThat(actual).isEqualTo(expected);

            verify(logger, times(2)).info(contains("create"), any(Subscriber.class));
        }
    }

    @Nested
    class ReadAll {
        @Test
        void readAllEmpty() {
            // Background

            // Given

            // When
            List<Subscriber> actual = cut.readAll();

            // Then
            assertThat(actual).isEmpty();

            verify(logger, times(1)).info(contains("readAll"), ArgumentMatchers.<List<Subscriber>>any());
        }

        @Test
        void readAllNonEmpty(@Random(size = 5, type = SubscriberEntity.class) List<SubscriberEntity> existent) {
            // Background
            when(subscriberEntityRepository.findAll()).thenReturn(existent);

            // Given

            // When
            List<Subscriber> actual = cut.readAll();

            // Then
            List<Subscriber> expected = existent.stream()
                    .map(e -> Subscriber.builder()
                            .withId(Identity.of(e.getId()))
                            .withName(e.getName())
                            .withEmail(e.getEmail())
                            .build())
                    .collect(Collectors.toList());

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);

            verify(logger, times(1)).info(contains("readAll"), ArgumentMatchers.<List<Subscriber>>any());
        }
    }

    @Nested
    class ReadByIdentity {
        @Test
        void readByIdentityNonExistent(@Random String nonExistentId) {
            // Background

            // Given
            Identity nonExistentIdentity = Identity.of(nonExistentId);

            // When
            Optional<Subscriber> actual = cut.readByIdentity(nonExistentIdentity);

            // Then
            assertThat(actual).isNotPresent();

            verify(logger, times(2)).info(contains("readByIdentity"), any(Object.class));
        }

        @Test
        void readByIdentityExistent(@Random SubscriberEntity existent) {
            // Background
            when(subscriberEntityRepository.findById(existent.getId())).thenReturn(Optional.of(existent));

            // Given
            Identity existentIdentity = Identity.of(existent.getId());

            // When
            Optional<Subscriber> actual = cut.readByIdentity(existentIdentity);

            // Then
            Subscriber expected = Subscriber.builder()
                    .withId(Identity.of(existent.getId()))
                    .withName(existent.getName())
                    .withEmail(existent.getEmail())
                    .build();

            assertThat(actual).isPresent().contains(expected);

            verify(logger, times(2)).info(contains("readByIdentity"), any(Object.class));
        }
    }

    @Nested
    class DeleteAll {
        @Test
        void deleteAllEmpty() {
            // Background

            // Given

            // When
            List<Subscriber> actual = cut.deleteAll();

            // Then
            assertThat(actual).isEmpty();

            verify(logger, times(1)).info(contains("deleteAll"), ArgumentMatchers.<List<Subscriber>>any());
        }

        @Test
        void deleteAllNonEmpty(@Random(size = 5, type = SubscriberEntity.class) List<SubscriberEntity> existent) {
            // Background
            when(subscriberEntityRepository.findAll()).thenReturn(existent);

            // Given

            // When
            List<Subscriber> actual = cut.deleteAll();

            // Then
            List<Subscriber> expected = existent.stream()
                    .map(e -> Subscriber.builder()
                            .withId(Identity.of(e.getId()))
                            .withName(e.getName())
                            .withEmail(e.getEmail())
                            .build()
                    ).collect(Collectors.toList());

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);

            verify(logger, times(1)).info(contains("deleteAll"), ArgumentMatchers.<List<Subscriber>>any());
        }
    }

    @Nested
    class DeleteByIdentity {
        @Test
        void deleteByIdentityExistent(@Random SubscriberEntity existent) {
            // Background
            when(subscriberEntityRepository.findById(existent.getId())).thenReturn(Optional.of(existent));

            // Given
            Identity existentIdentity = Identity.of(existent.getId());

            // When
            Optional<Subscriber> actual = cut.deleteByIdentity(existentIdentity);

            // Then
            Subscriber expected = Subscriber.builder()
                    .withId(Identity.of(existent.getId()))
                    .withName(existent.getName())
                    .withEmail(existent.getEmail())
                    .build();

            assertThat(actual).isPresent().contains(expected);

            verify(logger, times(2)).info(contains("deleteByIdentity"), any(Object.class));
        }

        @Test
        void deleteByIdentityNonExistent(@Random String nonExistentId) {
            // Background

            // Given
            Identity nonExistentIdentity = Identity.of(nonExistentId);

            // When
            Optional<Subscriber> actual = cut.deleteByIdentity(nonExistentIdentity);

            // Then
            assertThat(actual).isNotPresent();

            verify(logger, times(2)).info(contains("deleteByIdentity"), any(Object.class));
        }
    }

}