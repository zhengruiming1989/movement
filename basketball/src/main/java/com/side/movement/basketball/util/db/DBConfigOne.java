package com.side.movement.basketball.util.db;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
@Component("dBConfigOne")
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "mysql.datasource.test1")
public class DBConfigOne extends DBConfig{
	
}
 
 
