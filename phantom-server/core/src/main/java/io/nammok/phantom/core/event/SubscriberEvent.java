package io.nammok.phantom.core.event;

import io.nammok.phantom.core.domain.Subscriber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import static java.time.Instant.*;

@Data
@AllArgsConstructor
public class SubscriberEvent implements Serializable {

    @NonNull
    private final Subscriber body;
    private final Long createdDateMs = EPOCH.toEpochMilli();
}
