package com.side.movement.basketball.util.db;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component("dBConfigTwo")
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "mysql.datasource.test2")
public class DBConfigTwo extends DBConfig{

}
 
 
