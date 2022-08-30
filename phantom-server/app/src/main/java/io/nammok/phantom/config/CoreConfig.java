package io.nammok.phantom.config;

import io.nammok.phantom.core.event.PhantomEventBus;
import io.nammok.phantom.core.port.CircularRepository;
import io.nammok.phantom.core.port.SubscriberRepository;
import io.nammok.phantom.core.usecase.circular.*;
import io.nammok.phantom.core.usecase.identity.GenerateRandomIdentityUseCase;
import io.nammok.phantom.core.usecase.subscriber.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfig {
    @Bean
    public GenerateRandomIdentityUseCase generateRandomIdentityUseCase() {
        return new GenerateRandomIdentityUseCase();
    }

    @Bean
    public CreateCircularUseCase createCircularUseCase(CircularRepository repository,
                                                       GenerateRandomIdentityUseCase generateRandomIdentityUseCase,
                                                       PhantomEventBus eventBus) {
        return new CreateCircularUseCase(repository, generateRandomIdentityUseCase, eventBus);
    }

    @Bean
    public ReadAllCircularUseCase readAllCircularUseCase(CircularRepository repository) {
        return new ReadAllCircularUseCase(repository);
    }

    @Bean
    public ReadCircularUseCase readCircularUseCase(CircularRepository repository) {
        return new ReadCircularUseCase(repository);
    }

    @Bean
    public DeleteAllCircularUseCase deleteAllCircularUseCase(CircularRepository repository, PhantomEventBus phantomEventBus) {
        return new DeleteAllCircularUseCase(repository, phantomEventBus);
    }

    @Bean
    public DeleteCircularUseCase deleteCircularUseCase(CircularRepository repository, PhantomEventBus phantomEventBus) {
        return new DeleteCircularUseCase(repository, phantomEventBus);
    }

    @Bean
    public CreateSubscriberUseCase createSubscriberUseCase(SubscriberRepository repository,
                                                           GenerateRandomIdentityUseCase generateRandomIdentityUseCase,
                                                           PhantomEventBus eventBus) {
        return new CreateSubscriberUseCase(repository, generateRandomIdentityUseCase, eventBus);
    }

    @Bean
    public ReadAllSubscriberUseCase readAllSubscriberUseCase(SubscriberRepository repository) {
        return new ReadAllSubscriberUseCase(repository);
    }

    @Bean
    public ReadSubscriberUseCase readSubscriberUseCase(SubscriberRepository repository) {
        return new ReadSubscriberUseCase(repository);
    }

    @Bean
    public DeleteAllSubscriberUseCase deleteAllSubscriberUseCase(SubscriberRepository repository, PhantomEventBus eventBus) {
        return new DeleteAllSubscriberUseCase(repository, eventBus);
    }

    @Bean
    public DeleteSubscriberUseCase deleteSubscriberUseCase(SubscriberRepository repository, PhantomEventBus eventBus) {
        return new DeleteSubscriberUseCase(repository, eventBus);
    }
}
