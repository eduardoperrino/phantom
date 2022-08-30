package io.nammok.phantom.config;

import io.nammok.phantom.core.port.CircularRepository;
import io.nammok.phantom.core.port.SubscriberRepository;
import io.nammok.phantom.repository.springdata.CircularEntityRepository;
import io.nammok.phantom.repository.springdata.SubscriberEntityRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = DataConfig.class)
class DataConfigTest {
    @MockBean
    Logger logger;
    @MockBean
    CircularEntityRepository circularEntityRepository;
    @MockBean
    SubscriberEntityRepository subscriberEntityRepository;

    @Autowired
    CircularRepository circularRepository;
    @Autowired
    SubscriberRepository subscriberRepository;

    @Test
    void circularRepository() {
        assertNotNull(circularRepository);
    }

   @Test
   void subscriberRepository() { assertNotNull(subscriberRepository); }
}
