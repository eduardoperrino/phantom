package io.nammok.phantom.repository.springdata;

import io.nammok.phantom.core.domain.Circular;
import io.nammok.phantom.core.domain.Identity;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import io.nammok.phantom.repository.springdata.entity.CircularEntity;
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
import static org.mockito.Mockito.*;

@ExtendWith(RandomBeansExtension.class)
@ExtendWith(MockitoExtension.class)
class CircularRepositoryImplTest {
    @Mock
    Logger logger;
    @Mock
    CircularEntityRepository circularEntityRepository;

    @InjectMocks
    CircularRepositoryImpl cut;

    @Nested
    class Create {
        @Test
        void create(@Random CircularEntity existent) {
            // Background
            when(circularEntityRepository.save(existent)).thenReturn(existent);

            // Given
            Circular toCreate = Circular.builder()
                    .withId(Identity.of(existent.getId()))
                    .withName(existent.getName())
                    .withDescription(existent.getDescription())
                    .build();

            // When
            Circular actual = cut.create(toCreate);

            // Then
            Circular expected = Circular.builder()
                    .withId(Identity.of(existent.getId()))
                    .withName(existent.getName())
                    .withDescription(existent.getDescription())
                    .build();

            assertThat(actual).isEqualTo(expected);

            verify(logger, times(2)).info(contains("create"), any(Circular.class));
        }
    }

    @Nested
    class ReadAll {
        @Test
        void readAllEmpty() {
            // Background

            // Given

            // When
            List<Circular> actual = cut.readAll();

            // Then
            assertThat(actual).isEmpty();

            verify(logger, times(1)).info(contains("readAll"), ArgumentMatchers.<List<Circular>>any());
        }

        @Test
        void readAllNonEmpty(@Random(size = 5, type = CircularEntity.class) List<CircularEntity> existent) {
            // Background
            when(circularEntityRepository.findAll()).thenReturn(existent);

            // Given

            // When
            List<Circular> actual = cut.readAll();

            // Then
            List<Circular> expected = existent.stream()
                    .map(e -> Circular.builder()
                            .withId(Identity.of(e.getId()))
                            .withName(e.getName())
                            .withDescription(e.getDescription())
                            .build())
                    .collect(Collectors.toList());

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);

            verify(logger, times(1)).info(contains("readAll"), ArgumentMatchers.<List<Circular>>any());
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
            Optional<Circular> actual = cut.readByIdentity(nonExistentIdentity);

            // Then
            assertThat(actual).isNotPresent();

            verify(logger, times(2)).info(contains("readByIdentity"), any(Object.class));
        }

        @Test
        void readByIdentityExistent(@Random CircularEntity existent) {
            // Background
            when(circularEntityRepository.findById(existent.getId())).thenReturn(Optional.of(existent));

            // Given
            Identity existentIdentity = Identity.of(existent.getId());

            // When
            Optional<Circular> actual = cut.readByIdentity(existentIdentity);

            // Then
            Circular expected = Circular.builder()
                    .withId(Identity.of(existent.getId()))
                    .withName(existent.getName())
                    .withDescription(existent.getDescription())
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
            List<Circular> actual = cut.deleteAll();

            // Then
            assertThat(actual).isEmpty();

            verify(logger, times(1)).info(contains("deleteAll"), ArgumentMatchers.<List<Circular>>any());
        }

        @Test
        void deleteAllNonEmpty(@Random(size = 5, type = CircularEntity.class) List<CircularEntity> existent) {
            // Background
            when(circularEntityRepository.findAll()).thenReturn(existent);

            // Given

            // When
            List<Circular> actual = cut.deleteAll();

            // Then
            List<Circular> expected = existent.stream()
                    .map(e -> Circular.builder()
                            .withId(Identity.of(e.getId()))
                            .withName(e.getName())
                            .withDescription(e.getDescription())
                            .build()
                    ).collect(Collectors.toList());

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);

            verify(logger, times(1)).info(contains("deleteAll"), ArgumentMatchers.<List<Circular>>any());
        }
    }

    @Nested
    class DeleteByIdentity {
        @Test
        void deleteByIdentityExistent(@Random CircularEntity existent) {
            // Background
            when(circularEntityRepository.findById(existent.getId())).thenReturn(Optional.of(existent));

            // Given
            Identity existentIdentity = Identity.of(existent.getId());

            // When
            Optional<Circular> actual = cut.deleteByIdentity(existentIdentity);

            // Then
            Circular expected = Circular.builder()
                    .withId(Identity.of(existent.getId()))
                    .withName(existent.getName())
                    .withDescription(existent.getDescription())
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
            Optional<Circular> actual = cut.deleteByIdentity(nonExistentIdentity);

            // Then
            assertThat(actual).isNotPresent();

            verify(logger, times(2)).info(contains("deleteByIdentity"), any(Object.class));
        }
    }
}
