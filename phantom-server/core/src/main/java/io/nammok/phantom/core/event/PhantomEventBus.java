package io.nammok.phantom.core.event;

public interface PhantomEventBus {
    void register(EventListener listener);
    void post(CircularEvent event);
}
