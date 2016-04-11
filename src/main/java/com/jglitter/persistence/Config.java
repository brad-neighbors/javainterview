package com.jglitter.persistence;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import javax.sql.DataSource;

/**
 * Spring configuration for the persistence area, includes Liquibase to perform database schema creation,
 * entity discovery and repository component discovery.
 */
@Configuration
@ComponentScan(basePackages = {"com.jglitter.persistence"})
@EnableAutoConfiguration(exclude = {LiquibaseAutoConfiguration.class})
@EnableSpringDataWebSupport
@EntityScan(basePackages = {"com.jglitter.domain"})
public class Config {

    @Autowired
    private DataSource dataSource;

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setDropFirst(true);
        springLiquibase.setChangeLog("classpath:/db-changelog.xml");
        springLiquibase.setDataSource(dataSource);
        return springLiquibase;
    }

}
