package io.nammok.phantom.core.event;

public interface EventListener<T extends CircularEvent> {
     void onEvent(T event);
}
