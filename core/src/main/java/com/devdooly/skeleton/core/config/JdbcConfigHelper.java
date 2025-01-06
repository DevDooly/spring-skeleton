package com.devdooly.skeleton.core.config;

import com.devdooly.skeleton.core.properties.JdbcConfigProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import javax.sql.DataSource;

public class JdbcConfigHelper {

    public static HikariDataSource getDataSource(JdbcConfigProperties jdbcConfigProperties) {
        HikariConfig config = new HikariConfig();
        config.setPoolName(jdbcConfigProperties.getPoolName());
        config.setDriverClassName(jdbcConfigProperties.getDriverClassName());
        config.setJdbcUrl(jdbcConfigProperties.getJdbcUrl());
        config.setUsername(jdbcConfigProperties.getUsername());
        config.setPassword(jdbcConfigProperties.getPassword());
        config.setMaximumPoolSize(jdbcConfigProperties.getMaximumPoolSize());
        config.setMinimumIdle(jdbcConfigProperties.getMinumumIdle());
        config.setConnectionTimeout(jdbcConfigProperties.getConnectionTimeout());
        config.setValidationTimeout(jdbcConfigProperties.getValidationTimeout());
        config.setIdleTimeout(jdbcConfigProperties.getIdleTimeout());
        config.setMaxLifetime(jdbcConfigProperties.getMaxLifetime());
        return new HikariDataSource(config);
    }

    public static Jdbi getJdbi(DataSource dataSource) {
        Jdbi jdbi = Jdbi.create(dataSource);
        jdbi.installPlugin(new SqlObjectPlugin());
        return jdbi;
    }

    public static <T> T bindDataSource(DataSource dataSource, Class<T> type) {
        return getJdbi(dataSource).onDemand(type);
    }
}

