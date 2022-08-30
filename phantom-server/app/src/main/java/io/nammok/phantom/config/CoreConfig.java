package io.nammok.phantom.config;

import io.nammok.phantom.core.domain.subscriber.*;
import io.nammok.phantom.core.event.PhantomEventBus;
import io.nammok.phantom.core.port.CircularRepository;
import io.nammok.phantom.core.port.SubscriberRepository;
import io.nammok.phantom.core.usecase.circular.*;
import io.nammok.phantom.core.usecase.identity.GenerateRandomIdentityUseCase;
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
                                                       PhantomEventBus phantomEventBus) {
        return new CreateCircularUseCase(repository, generateRandomIdentityUseCase, phantomEventBus);
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
    public DeleteAllCircularUseCase deleteAllCircularUseCase(CircularRepository repository) {
        return new DeleteAllCircularUseCase(repository);
    }

    @Bean
    public DeleteCircularUseCase deleteCircularUseCase(CircularRepository repository) {
        return new DeleteCircularUseCase(repository);
    }

    @Bean
    public CreateSubscriberUseCase createSubscriberUseCase(SubscriberRepository repository, GenerateRandomIdentityUseCase generateRandomIdentityUseCase) {
        return new CreateSubscriberUseCase(repository, generateRandomIdentityUseCase);
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
    public DeleteAllSubscriberUseCase deleteAllSubscriberUseCase(SubscriberRepository repository) {
        return new DeleteAllSubscriberUseCase(repository);
    }

    @Bean
    public DeleteSubscriberUseCase deleteSubscriberUseCase(SubscriberRepository repository) {
        return new DeleteSubscriberUseCase(repository);
    }
}
