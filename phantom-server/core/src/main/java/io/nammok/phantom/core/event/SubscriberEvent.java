package io.nammok.phantom.core.event;

import io.nammok.phantom.core.domain.Subscriber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.io.Serializable;
import static java.time.Instant.*;

@Value
@NonFinal
@AllArgsConstructor
public class SubscriberEvent implements Serializable {

    @NonNull
    Subscriber body;
    Long createdDateMs = EPOCH.toEpochMilli();
}
