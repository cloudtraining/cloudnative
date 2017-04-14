package com.arity.security;

/**
 * Created by sherjeelg on 4/14/2017.
 */
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.arity.security"})
@EnableJpaRepositories(basePackages = {"com.arity.security"})
@EnableTransactionManagement
public class RepositoryConfiguration {
}