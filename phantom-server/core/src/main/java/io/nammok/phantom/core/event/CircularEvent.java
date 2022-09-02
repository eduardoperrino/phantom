package io.nammok.phantom.core.event;

import io.nammok.phantom.core.domain.Circular;
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
public class CircularEvent implements Serializable {

    @NonNull
    Circular body;
    Long createdDateMs =  EPOCH.toEpochMilli();
}
