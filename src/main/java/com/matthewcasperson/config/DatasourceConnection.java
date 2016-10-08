package com.matthewcasperson.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnJndi;
import org.springframework.boot.autoconfigure.condition.NoneNestedConditions;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiTemplate;

import java.util.Properties;

import javax.sql.DataSource;

/**
 * Setup a connection to the database
 */
@Configuration
public class DatasourceConnection {

    private static final String H2_JDBC = "jdbc:h2:mem:microservice;DB_CLOSE_DELAY=-1;INIT=CREATE SCHEMA IF NOT EXISTS MicroserviceDatabase";
    private static final String H2_USER = "";
    private static final String H2_PASSWORD = "";
    private static final String H2_DRIVER = "org.h2.Driver";

    /**
     * This is where Tomcat has created our datasource
     */
    private static final String LOOKUP_DATASOURCE_JNDI = "java:/comp/env/jdbc/microservice";

    @Bean(name = "MicroserviceDS")
    @Profile("!TEST")
    public DataSource jndiLookupDataSource() throws Exception {
        return (DataSource) new JndiTemplate().lookup(LOOKUP_DATASOURCE_JNDI);
    }

    /**
     * When running tests we connect to an in memory H2 database
     */
    @Bean(name = "MicroserviceDS")
    @Profile("TEST")
    public DataSource driverManagerLookupDataSource() throws Exception {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(H2_DRIVER);
        dataSource.setUrl(H2_JDBC);
        dataSource.setUsername(H2_USER);
        dataSource.setPassword(H2_PASSWORD);

        return dataSource;
    }
}
