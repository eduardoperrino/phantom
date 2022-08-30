package io.nammok.phantom.config;


import io.nammok.phantom.core.domain.subscriber.*;
import io.nammok.phantom.core.event.PhantomEventBus;
import io.nammok.phantom.core.usecase.circular.*;
import io.nammok.phantom.core.usecase.identity.GenerateRandomIdentityUseCase;
import io.nammok.phantom.repository.springdata.CircularEntityRepository;
import io.nammok.phantom.repository.springdata.SubscriberEntityRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {DataConfig.class, CoreConfig.class})
class CoreConfigTest {
    @MockBean
    Logger logger;
    @MockBean
    CircularEntityRepository circularEntityRepository;
    @MockBean
    SubscriberEntityRepository subscriberEntityRepository;
    @MockBean
    PhantomEventBus phantomEventBus;

    @Autowired
    GenerateRandomIdentityUseCase generateRandomIdentityUseCase;
    @Autowired
    CreateCircularUseCase createCircularUseCase;
    @Autowired
    ReadAllCircularUseCase readAllCircularUseCase;
    @Autowired
    ReadCircularUseCase readCircularUseCase;
    @Autowired
    DeleteAllCircularUseCase deleteAllCircularUseCase;
    @Autowired
    DeleteCircularUseCase deleteCircularUseCase;
    @Autowired
    CreateSubscriberUseCase createSubscriberUseCase;
    @Autowired
    ReadAllSubscriberUseCase readAllSubscriberUseCase;
    @Autowired
    ReadSubscriberUseCase readSubscriberUseCase;
    @Autowired
    DeleteAllSubscriberUseCase deleteAllSubscriberUseCase;
    @Autowired
    DeleteSubscriberUseCase deleteSubscriberUseCase;

    @Test
    void generateRandomIdentityUseCase() {
        assertNotNull(generateRandomIdentityUseCase);
    }

    @Test
    void createCircularUseCase() {
        assertNotNull(createCircularUseCase);
    }

    @Test
    void readAllCircularUseCase() {
        assertNotNull(readAllCircularUseCase);
    }

    @Test
    void readCircularUseCase() {
        assertNotNull(readCircularUseCase);
    }

    @Test
    void deleteAllCircularUseCase() {
        assertNotNull(deleteAllCircularUseCase);
    }

    @Test
    void deleteCircularUseCase() {
        assertNotNull(deleteCircularUseCase);
    }

    @Test
    void createSubscriberUserCase() { assertNotNull(createSubscriberUseCase);}

    @Test
    void readAllSubscriberUseCase() { assertNotNull(readAllSubscriberUseCase); }

    @Test
    void readSubscriberUseCase() { assertNotNull(readSubscriberUseCase); }

    @Test
    void deleteAllSubscriberUseCase() { assertNotNull(deleteAllSubscriberUseCase); }

    @Test
    void deleteSubscriberUseCase() { assertNotNull(deleteSubscriberUseCase); }
}
