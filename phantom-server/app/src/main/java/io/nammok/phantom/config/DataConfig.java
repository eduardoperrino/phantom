package io.nammok.phantom.config;

import io.nammok.phantom.core.port.CircularRepository;
import io.nammok.phantom.core.port.SubscriberRepository;
import io.nammok.phantom.repository.springdata.CircularEntityRepository;
import io.nammok.phantom.repository.springdata.SubscriberEntityRepository;
import io.nammok.phantom.repository.springdata.CircularRepositoryImpl;
import io.nammok.phantom.repository.springdata.SubscriberRepositoryImpl;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataConfig {
    @Bean
    public CircularRepository circularRepository(Logger logger, CircularEntityRepository circularEntityRepository) {
        return new CircularRepositoryImpl(logger, circularEntityRepository);
    }

    @Bean
    public SubscriberRepository subscriberRepository(Logger logger, SubscriberEntityRepository subscriberEntityRepository) {
        return new SubscriberRepositoryImpl(logger, subscriberEntityRepository);
    }
}
