package io.nammok.phantom.presenter.rest.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(builderClassName = "Builder", setterPrefix = "with")
@JsonDeserialize(builder = SubscriberRequest.Builder.class)
public class SubscriberRequest {
    String name;
    String email;
}
