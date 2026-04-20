package com.devdooly.skeleton.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "skeleton.core")
public class CoreProperties {
    private boolean debug = false;
    private String serverId = "unknown";
}
