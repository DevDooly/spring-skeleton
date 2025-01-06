package com.devdooly.skeleton.core.config;

import com.devdooly.skeleton.core.properties.JdbcConfigProperties;
import com.devdooly.skeleton.core.repository.JdbcRepository;
import com.devdooly.skeleton.core.service.JdbcDataService;
import com.devdooly.skeleton.core.service.JdbcDataServiceImpl;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JdbcConfig {

    @Bean
    @ConfigurationProperties(prefix = "skeleton.datasource.jdbc")
    public JdbcConfigProperties jdbcConfigProperties() {
        return new JdbcConfigProperties();
    }

    @Bean
    public JdbcDataService jdbcDataService(JdbcConfigProperties jdbcConfigProperties) {
        return buildJdbcDataService(jdbcConfigProperties);
    }

    private static JdbcDataService buildJdbcDataService(JdbcConfigProperties jdbcConfigProperties) {
        HikariDataSource dataSource = JdbcConfigHelper.getDataSource(jdbcConfigProperties);
        JdbcRepository jdbcRepository = JdbcConfigHelper.bindDataSource(dataSource, JdbcRepository.class);
        return new JdbcDataServiceImpl(jdbcRepository, dataSource::close);
    }
}
