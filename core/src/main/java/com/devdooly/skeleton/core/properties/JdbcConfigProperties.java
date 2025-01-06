package com.devdooly.skeleton.core.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JdbcConfigProperties {
    private String poolName;
    private String driverClassName;
    private String jdbcUrl;
    private String username;
    private String password;
    private int maximumPoolSize;
    private int minumumIdle;
    private int connectionTimeout;
    private int validationTimeout;
    private int idleTimeout;
    private int maxLifetime;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(System.lineSeparator());
        sb.append("====================").append(System.lineSeparator());
        sb.append(" poolName=").append(poolName).append(System.lineSeparator());
        return sb.toString();
    }
}
