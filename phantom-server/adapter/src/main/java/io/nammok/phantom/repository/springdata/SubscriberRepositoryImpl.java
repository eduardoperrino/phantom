package io.nammok.phantom.repository.springdata;

import io.nammok.phantom.core.domain.Identity;
import io.nammok.phantom.core.domain.Subscriber;
import io.nammok.phantom.core.port.SubscriberRepository;
import io.nammok.phantom.repository.springdata.entity.SubscriberEntity;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SubscriberRepositoryImpl implements SubscriberRepository {

    private Logger logger;

    private SubscriberEntityRepository repository;

    public SubscriberRepositoryImpl(Logger logger, SubscriberEntityRepository repository) {
        this.logger = logger;
        this.repository = repository;
    }

    @Override
    public Subscriber create(Subscriber subscriber) {
        logger.info("create({})", subscriber);

        SubscriberEntity entity = SubscriberEntity.builder()
                .withId(subscriber.getId().getId())
                .withName(subscriber.getName())
                .withEmail(subscriber.getEmail())
                .build();

        SubscriberEntity saved = repository.save(entity);

        Subscriber created = Subscriber.builder()
                .withId(Identity.of(saved.getId()))
                .withName(saved.getName())
                .withEmail(saved.getEmail())
                .build();

        logger.info("create(): {}", created);
        return created;
    }

    @Override
    public List<Subscriber> readAll() {
        List<Subscriber> existing = StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(t -> Subscriber.builder()
                        .withId(Identity.of(t.getId()))
                        .withName(t.getName())
                        .withEmail(t.getEmail())
                        .build()
                ).collect(Collectors.toList());

        logger.info("readAll(): {}", existing);
        return existing;
    }

    @Override
    public Optional<Subscriber> readByIdentity(Identity identity) {
        logger.info("readByIdentity({})", identity);

        Optional<SubscriberEntity> se = repository.findById(identity.getId());
        Optional<Subscriber> read = se.map(t -> Subscriber.builder()
                .withId(Identity.of(t.getId()))
                .withName(t.getName())
                .withEmail(t.getEmail())
                .build());

        logger.info("readByIdentity(): {}", read);
        return read;
    }

    @Override
    public List<Subscriber> deleteAll() {
        List<Subscriber> deleted = readAll();
        repository.deleteAll();

        logger.info("deleteAll(): {}", deleted);
        return deleted;
    }

    @Override
    public Optional<Subscriber> deleteByIdentity(Identity identity) {
        logger.info("deleteByIdentity({})", identity);

        Optional<Subscriber> toDelete = readByIdentity(identity);
        if (toDelete.isPresent()) {
            repository.deleteById(identity.getId());
        }

        logger.info("deleteByIdentity(): {}", toDelete);
        return toDelete;
    }
}
