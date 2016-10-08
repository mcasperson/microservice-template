package com.matthewcasperson.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Set up JPA EMF for lookup database
 */
@Configuration
public class DatasourceEmfConfiguration {

    /**
     * Prod code uses MySQL databases
     */
    @Bean(name = "MicroserviceEMF")
    @Profile("!TEST")
    public EntityManagerFactory lookupEntityManagerFactory(
        @Qualifier("MicroserviceDS") final DataSource lookupDataSource
    ) {

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);

        final Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

        final LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setJpaPropertyMap(properties);
        factory.setPackagesToScan("com.matthewcasperson.domain.**");
        factory.setDataSource(lookupDataSource);
        factory.setPersistenceUnitName("LookupPersistenceUnit");
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    /**
     * When running tests we will be using an in memory h2 database
     */
    @Bean(name = "MicroserviceEMF")
    @Profile("TEST")
    public EntityManagerFactory lookupTestEntityManagerFactory(
        @Qualifier("MicroserviceDS") final DataSource lookupDataSource
    ) {

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);

        final Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.put("hibernate.current_session_context_class", "org.springframework.orm.hibernate5.SpringSessionContext");

        final LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setJpaPropertyMap(properties);
        factory.setPackagesToScan("com.matthewcasperson.domain.**");
        factory.setDataSource(lookupDataSource);
        factory.setPersistenceUnitName("LookupPersistenceUnit");
        factory.afterPropertiesSet();

        return factory.getObject();
    }
}
