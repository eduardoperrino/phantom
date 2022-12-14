package io.nammok.phantom.repository.springdata;


import io.nammok.phantom.core.domain.Circular;
import io.nammok.phantom.core.domain.Identity;
import io.nammok.phantom.core.port.CircularRepository;
import io.nammok.phantom.repository.springdata.entity.CircularEntity;
import org.slf4j.Logger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CircularRepositoryImpl implements CircularRepository {
    private final Logger logger;

    private final CircularEntityRepository repository;

    public CircularRepositoryImpl(Logger logger, CircularEntityRepository circularEntityRepository) {
        this.logger = logger;
        this.repository = circularEntityRepository;
    }

    @Override
    public Circular create(Circular circular) {
        logger.info("create({})", circular);

        CircularEntity entity = CircularEntity.builder()
                .withId(circular.getId().getId())
                .withName(circular.getName())
                .withDescription(circular.getDescription())
                .build();

        CircularEntity saved = repository.save(entity);

        Circular created = Circular.builder()
                .withId(Identity.of(saved.getId()))
                .withName(saved.getName())
                .withDescription(saved.getDescription())
                .build();

        logger.info("create(): {}", created);
        return created;
    }

    @Override
    public List<Circular> readAll() {
        List<Circular> existing = StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(t -> Circular.builder()
                        .withId(Identity.of(t.getId()))
                        .withName(t.getName())
                        .withDescription(t.getDescription())
                        .build()
                ).collect(Collectors.toList());

        logger.info("readAll(): {}", existing);
        return existing;
    }

    @Override
    public Optional<Circular> readByIdentity(Identity identity) {
        logger.info("readByIdentity({})", identity);

        Optional<CircularEntity> ce = repository.findById(identity.getId());
        Optional<Circular> read = ce.map(t -> Circular.builder()
                .withId(Identity.of(t.getId()))
                .withName(t.getName())
                .withDescription(t.getDescription())
                .build());

        logger.info("readByIdentity(): {}", read);
        return read;
    }

    @Override
    public List<Circular> deleteAll() {
        List<Circular> deleted = readAll();
        repository.deleteAll();

        logger.info("deleteAll(): {}", deleted);
        return deleted;
    }

    @Override
    public Optional<Circular> deleteByIdentity(Identity identity) {
        logger.info("deleteByIdentity({})", identity);

        Optional<Circular> toDelete = readByIdentity(identity);
        if (toDelete.isPresent()) {
            repository.deleteById(identity.getId());
        }

        logger.info("deleteByIdentity(): {}", toDelete);
        return toDelete;
    }
}
