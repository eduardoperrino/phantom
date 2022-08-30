package io.nammok.phantom.presenter.rest.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(RandomBeansExtension.class)
class SubscriberResponseTest {
    private final Class<SubscriberResponse> CLAZZ = SubscriberResponse.class;

    SubscriberResponse.Builder cut;

    @BeforeEach
    void setUp() {
        this.cut = SubscriberResponse.builder();
    }

    @Test
    void getId(@Random String id) {
        // Given
        cut.withId(id);

        // When
        SubscriberResponse actual = cut.build();

        // Then
        assertThat(actual).isNotNull().satisfies(subscriberResponse -> {
            assertThat(subscriberResponse.getId()).isEqualTo(id);
            assertThat(subscriberResponse.getName()).isNull();
            assertThat(subscriberResponse.getEmail()).isNull();
        });
    }

    @Test
    void getName(@Random String name) {
        // Given
        cut.withName(name);

        // When
        SubscriberResponse actual = cut.build();

        // Then
        assertThat(actual).isNotNull().satisfies(subscriberResponse -> {
            assertThat(subscriberResponse.getId()).isNull();
            assertThat(subscriberResponse.getName()).isEqualTo(name);
            assertThat(subscriberResponse.getEmail()).isNull();
        });
    }

    @Test
    void getEmail(@Random String email) {
        // Given
        cut.withEmail(email);

        // When
        SubscriberResponse actual = cut.build();

        // Then
        assertThat(actual).isNotNull().satisfies(subscriberResponse -> {
            assertThat(subscriberResponse.getId()).isNull();
            assertThat(subscriberResponse.getName()).isNull();
            assertThat(subscriberResponse.getEmail()).isEqualTo(email);
        });
    }

    @Nested
    class Builder {
        @Test
        void nullObject() {
            // Given

            // When
            SubscriberResponse actual = cut.build();

            // Then
            assertThat(actual).isNotNull().satisfies(subscriberResponse -> {
                assertThat(subscriberResponse.getId()).isNull();
                assertThat(subscriberResponse.getName()).isNull();
                assertThat(subscriberResponse.getEmail()).isNull();
            });
        }

        @Test
        void fullObject(@Random String id, @Random String name, @Random String email) {
            cut
                    .withId(id)
                    .withName(name)
                    .withEmail(email);

            // When
            SubscriberResponse actual = cut.build();

            // Then
            assertThat(actual).isNotNull().satisfies(subscriberResponse -> {
                assertThat(subscriberResponse.getId()).isEqualTo(id);
                assertThat(subscriberResponse.getName()).isEqualTo(name);
                assertThat(subscriberResponse.getEmail()).isEqualTo(email);
            });
        }
    }

    @Nested
    class JSON {
        @Test
        void serializationDeserialization(@Random String id, @Random String name, @Random String email) throws JsonProcessingException {
            // Background
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

            // Given
            String json = String.format("{\n" +
                    "  \"id\":\"%s\",\n" +
                    "  \"name\":\"%s\",\n" +
                    "  \"email\":\"%s\"\n" +
                    "}", id, name, email);

            // When
            SubscriberResponse entityFromBuilder = cut.withId(id).withName(name).withEmail(email).build();
            String actualJson = mapper.writeValueAsString(entityFromBuilder);

            // Then
            SubscriberResponse entityFromJson = mapper.readValue(json, CLAZZ);
            String expectedJson = mapper.writeValueAsString(entityFromJson);

            assertThat(actualJson).isEqualTo(expectedJson);
            assertThat(mapper.readValue(actualJson, CLAZZ))
                    .isEqualTo(mapper.readValue(expectedJson, CLAZZ));
            assertThat(mapper.readValue(actualJson, CLAZZ).toString())
                    .isEqualTo(mapper.readValue(expectedJson, CLAZZ).toString());
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

        @Test
        void testEquals() {
            EqualsVerifier.forClass(CLAZZ)
                    .verify();
        }
    }

}