package io.nammok.phantom.core.event;


import io.nammok.phantom.core.domain.Circular;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import static java.time.Instant.*;

@Data
@AllArgsConstructor
public class CircularEvent implements Serializable {

    @NonNull
    private final Circular body;
    private final Long createdDateMs =  EPOCH.toEpochMilli();
}
