package io.nammok.phantom.core.port;

import io.nammok.phantom.core.domain.Identity;
import io.nammok.phantom.core.domain.Subscriber;

import java.util.List;
import java.util.Optional;

public interface SubscriberRepository {
    /**
     * Create a {@link Subscriber}
     *
     * @param subscriber The {@link Subscriber} to create
     * @return The {@link Subscriber} created
     */
    Subscriber create(Subscriber subscriber);

    /**
     * Read all existing {@link Subscriber}
     *
     * @return A list of read {@link Subscriber}, empty list if none exist
     */
    List<Subscriber> readAll();

    /**
     * Read a {@link Subscriber} by {@link Identity}
     *
     * @param identity The {@link Identity} to read the {@link Subscriber}
     * @return The {@link Subscriber} found for {@link Identity}, empty {@link Optional} if none exist
     */
    Optional<Subscriber> readByIdentity(Identity identity);

    /**
     * Delete all existing {@link Subscriber}
     *
     * @return A list of deleted {@link Subscriber}, empty list if none deleted
     */
    List<Subscriber> deleteAll();

    /**
     * Delete a {@link Subscriber} by {@link Identity}
     *
     * @param identity The {@link Identity} to delete the {@link Subscriber}
     * @return The {@link Subscriber} deleted for {@link Identity}, empty {@link Optional} if none deleted
     */
    Optional<Subscriber> deleteByIdentity(Identity identity);
}
