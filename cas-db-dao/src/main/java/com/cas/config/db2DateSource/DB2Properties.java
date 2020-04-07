package com.cas.config.db2DateSource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.db2")
public class DB2Properties {
 
    private String url;
 
    private String username;
 
    private String password;
 
    private Integer minPoolSize;
 
    private Integer maxPoolSize;
 
    private Integer maxLifetime;
 
    private Integer borrowConnectionTimeout;
 
    private Integer loginTimeout;
 
    private Integer maintenanceInterval;
 
    private Integer maxIdleTime;
 
    private String testQuery;
 
    private String mapperLocations;
 
    private String typeAliasesPackage;
 
 
}