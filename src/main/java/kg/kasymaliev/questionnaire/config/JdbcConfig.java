package kg.kasymaliev.questionnaire.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = "kg.kasymaliev.questionnaire.repository",
        transactionManagerRef = "defaultTransactionManager",
        entityManagerFactoryRef = "defaultEntityManager")
public class JdbcConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String databaseUrl;

    @Value("${spring.datasource.username}")
    private String dbUserName;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    /**
     * Transaction manager bean.
     *
     * @param entityManagerFactory - em factory.
     * @return {@link PlatformTransactionManager}
     */
    @Primary
    @Bean(name = "defaultTransactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    /**
     * EntityManagerFactory bean.
     *
     * @param dataSource - datasource
     * @return {@link LocalContainerEntityManagerFactoryBean}
     */
    @Primary
    @Bean(name = "defaultEntityManager")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(@Qualifier("defaultDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emFactory = new LocalContainerEntityManagerFactoryBean();
        emFactory.setDataSource(dataSource);
        emFactory.setPackagesToScan("kg.kasymaliev.questionnaire.entity");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        emFactory.setJpaVendorAdapter(vendorAdapter);
        emFactory.setJpaProperties(additionalProperties());

        return emFactory;
    }


    /**
     * DataSource bean
     *
     * @return {@link DataSource}
     */
    @Bean(name = "defaultDataSource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(databaseUrl);
        dataSource.setUsername(dbUserName);
        dataSource.setPassword(dbPassword);
        return dataSource;
    }



    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
        properties.setProperty("hibernate.jdbc.lob.non_contextual_creation", "true");
        return properties;
    }


}
