package io.nammok.phantom.repository.springdata;

import io.nammok.phantom.repository.springdata.entity.SubscriberEntity;
import org.springframework.data.repository.CrudRepository;

public interface SubscriberEntityRepository extends CrudRepository<SubscriberEntity, String> {
}
