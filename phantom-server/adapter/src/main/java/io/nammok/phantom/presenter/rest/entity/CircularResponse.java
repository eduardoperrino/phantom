package io.nammok.phantom.presenter.rest.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(builderClassName = "Builder", setterPrefix = "with")
@JsonDeserialize(builder = CircularResponse.Builder.class)
public class CircularResponse {
    String id;
    String name;
    String description;
}
