package com.matthewcasperson.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;

/**
 * Set up transaction manager for lookup database
 */
public class DatasourceTXManagerConfiguration {

    @Bean(name = "MicroserviceTX")
    public PlatformTransactionManager lookupTransactionManager(
        @Qualifier("MicroserviceEMF") EntityManagerFactory lookupEntityManagerFactory
    ) {
        final JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(lookupEntityManagerFactory);
        return txManager;
    }
}
