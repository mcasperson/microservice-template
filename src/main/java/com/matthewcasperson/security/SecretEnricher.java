package com.matthewcasperson.security;

import com.matthewcasperson.domain.MicroserviceKeyValue;
import com.yahoo.elide.core.Initializer;
import org.springframework.stereotype.Component;

/**
 * Created by matthewcasperson on 8/10/16.
 */
@Component
public class SecretEnricher implements Initializer<MicroserviceKeyValue> {
    @Override
    public void initialize(MicroserviceKeyValue entity) {
        entity.setSecret(entity.getKey() + "Secret");
    }
}
