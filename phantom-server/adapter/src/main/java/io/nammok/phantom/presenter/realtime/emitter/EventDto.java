package io.nammok.phantom.presenter.realtime.emitter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder", setterPrefix = "with", toBuilder = true)
public class EventDto implements Serializable {

    private EventType type;
    private String body;

    public enum EventType {
        MESSAGE("message");

        private String type;
        private EventType(String type) {
            this.type = type;
        }

        public String getType() {
            return this.type;
        }
    }
}
