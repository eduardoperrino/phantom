package io.nammok.phantom.repository.springdata;

import io.nammok.phantom.repository.springdata.entity.CircularEntity;
import org.springframework.data.repository.CrudRepository;

public interface CircularEntityRepository extends CrudRepository<CircularEntity, String> {
}
