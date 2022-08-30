package io.nammok.phantom.presenter.rest.subscriber;

import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import io.nammok.phantom.core.domain.Subscriber;
import io.nammok.phantom.presenter.rest.entity.SubscriberResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(RandomBeansExtension.class)
class SubscriberMapperTest {
    SubscriberMapper cut;

    @BeforeEach
    void setUp() { this.cut = new SubscriberMapper(); }

    @Test
    void convertEntityToResponse(@Random Subscriber entity) {
        // Given

        // When
        SubscriberResponse actual = cut.convertEntityToResponse(entity);

        // Then
        assertThat(actual).isNotNull().satisfies(circularResponse -> {
            assertThat(circularResponse.getId()).isEqualTo(entity.getId().getId());
            assertThat(circularResponse.getName()).isEqualTo(entity.getName());
            assertThat(circularResponse.getEmail()).isEqualTo(entity.getEmail());
        });
    }

}