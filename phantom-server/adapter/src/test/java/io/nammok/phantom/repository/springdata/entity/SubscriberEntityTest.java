package io.nammok.phantom.repository.springdata.entity;

import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(RandomBeansExtension.class)
class SubscriberEntityTest {
    private final Class<SubscriberEntity> CLAZZ = SubscriberEntity.class;

    SubscriberEntity.Builder cut;

    @BeforeEach
    void setUp() {
        this.cut = SubscriberEntity.builder();
    }

    @Test
    void getId(@Random String id) {
        // Given
        SubscriberEntity actual = new SubscriberEntity();

        // When
        actual.setId(id);

        // Then
        assertThat(actual).isNotNull().satisfies(subscriberEntity -> {
            assertThat(subscriberEntity.getId()).isEqualTo(id);
            assertThat(subscriberEntity.getName()).isNull();
            assertThat(subscriberEntity.getEmail()).isNull();
        });
    }

    @Test
    void getName(@Random String name) {
        // Given
        SubscriberEntity actual = new SubscriberEntity();

        // When
        actual.setName(name);

        // Then
        assertThat(actual).isNotNull().satisfies(subscriberEntity -> {
            assertThat(subscriberEntity.getId()).isNull();
            assertThat(subscriberEntity.getName()).isEqualTo(name);
            assertThat(subscriberEntity.getEmail()).isNull();
        });
    }

    @Test
    void getEmail(@Random String email) {
        // Given
        SubscriberEntity actual = new SubscriberEntity();

        // When
        actual.setEmail(email);

        // Then
        assertThat(actual).isNotNull().satisfies(subscriberEntity -> {
            assertThat(subscriberEntity.getId()).isNull();
            assertThat(subscriberEntity.getName()).isNull();
            assertThat(subscriberEntity.getEmail()).isEqualTo(email);
        });
    }

    @Nested
    class Builder {
        @Test
        void nullObject() {
            // Given

            // When
            SubscriberEntity actual = cut.build();

            // Then
            assertThat(actual).isNotNull().satisfies(subscriberEntity -> {
                assertThat(subscriberEntity.getId()).isNull();
                assertThat(subscriberEntity.getName()).isNull();
                assertThat(subscriberEntity.getEmail()).isNull();
            });
        }

        @Test
        void fullObject(@Random String id, @Random String name, @Random String email) {
            // Given
            cut
                    .withId(id)
                    .withName(name)
                    .withEmail(email);

            // When
            SubscriberEntity actual = cut.build();

            // Then
            assertThat(actual).isNotNull().satisfies(subscriberEntity -> {
                assertThat(subscriberEntity.getId()).isEqualTo(id);
                assertThat(subscriberEntity.getName()).isEqualTo(name);
                assertThat(subscriberEntity.getEmail()).isEqualTo(email);
            });
        }
    }

    @Nested
    class Override {
        @Test
        void testToString() {
            ToStringVerifier.forClass(CLAZZ)
                    .withClassName(NameStyle.SIMPLE_NAME)
                    .verify();
        }
    }
}