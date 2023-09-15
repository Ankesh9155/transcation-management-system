package com.example.microservice.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.microservice.repository",
        entityManagerFactoryRef = "OfficeTransactionEntityManager",
        transactionManagerRef = "OfficeTransactionManager"
)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JpaConfig {
    @Bean
    @Primary
    public DataSource OfficeTransactionDataSource() {

        final HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/organization");
        config.setUsername("root");
        config.setPassword("root");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return new HikariDataSource(config);
    }
    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean OfficeTransactionEntityManager() {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(OfficeTransactionDataSource());
        entityManager.setPackagesToScan(
                new String[]{"com.example.microservice.model.entity"});

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManager.setJpaVendorAdapter(vendorAdapter);
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.connection.url","jdbc:mysql://localhost:3306/organization");
        properties.put("hibernate.driver", "com.mysql.jdbc.Driver");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.connection.username", "root");
        properties.put("hibernate.connection.password", "root");

        entityManager.setJpaPropertyMap(properties);
        return entityManager;
    }

    @Bean
    @Primary
    public PlatformTransactionManager OfficeTransactionManager(
            @NonNull @Qualifier("OfficeTransactionEntityManager")
            LocalContainerEntityManagerFactoryBean OfficeTransactionEntityManager) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(OfficeTransactionEntityManager.getObject());
        return transactionManager;
    }
}
