package com.matthewcasperson.repository;

import com.matthewcasperson.domain.MicroserviceKeyValue;
import org.springframework.data.repository.CrudRepository;

/**
 * A repo for working with app quote mappings
 */
public interface MicroserviceRepository extends CrudRepository<MicroserviceKeyValue, Integer> {

}
