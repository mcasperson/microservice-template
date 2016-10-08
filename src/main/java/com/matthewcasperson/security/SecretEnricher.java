package com.matthewcasperson.security;

import com.matthewcasperson.domain.MicroserviceKeyValue;
import com.yahoo.elide.core.Initializer;
import org.springframework.stereotype.Component;

/**
 * This class is used to populate a computed attribute on the JPA entity.
 * In this way we are enriching the JPA entity at run time with information
 * that isn't stored in the database.
 *
 * Note that we have made this class a Spring component. We could inject
 * anything supported by Spring into this object, and it will work
 * with Elide.
 */
@Component
public class SecretEnricher implements Initializer<MicroserviceKeyValue> {
    @Override
    public void initialize(MicroserviceKeyValue entity) {
        /*
            Here we define the secret that is associated with the JPA entity.
            In this example the secret is just the key combined with the
            suffix "Secret", which obviously is not good security, but
            goes to show how this class can be used to enrich a JPA entity.
         */
        entity.setSecret(entity.getKey() + "Secret");
    }
}
