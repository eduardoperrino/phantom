package io.nammok.phantom.core.domain;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(builderClassName = "Builder", setterPrefix = "with")
public class Identity {
    String id;

    public static Identity of(String value) {
        return Identity.builder().withId(value).build();
    }

    public static Identity random() {
        return Identity.builder().withId(UUID.randomUUID().toString()).build();
    }
}
