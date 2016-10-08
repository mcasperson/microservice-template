package com.matthewcasperson.config;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring JPA configuration for app quote microservice data
 */
@Configuration
@EnableJpaRepositories(
    // This needs to match the entity manager factory created below
    entityManagerFactoryRef = "MicroserviceEMF",
    // This needs to match the transaction manager created below
    transactionManagerRef = "MicroserviceTX",
    // This is the package that holds the Spring repository using the classes exposed by this EMF
    basePackages = "com.matthewcasperson.repository"
)
@EnableTransactionManagement
@Import({
    DatasourceConnection.class,
    DatasourceEmfConfiguration.class,
    DatasourceTXManagerConfiguration.class
})
public class DatasourceConfiguration {
    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatFactory() {
        return new TomcatEmbeddedServletContainerFactory() {

            @Override
            protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(final Tomcat tomcat) {
                tomcat.enableNaming();
                return super.getTomcatEmbeddedServletContainer(tomcat);
            }

            @Override
            protected void postProcessContext(Context context) {
                ContextResource resource = new ContextResource();
                resource.setName("jdbc/microservice");
                resource.setType(DataSource.class.getName());
                resource.setProperty("factory", "org.apache.tomcat.jdbc.pool.DataSourceFactory");
                resource.setProperty("driverClassName", "com.mysql.jdbc.Driver");
                resource.setProperty("username", "root");
                resource.setProperty("password", "password1!");
                resource.setProperty("url", "jdbc:mysql://localhost:3306/microservice");

                context.getNamingResources().addResource(resource);
            }
        };
    }
}
