package com.side.movement.basketball.util.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import com.side.movement.basketball.util.db.DBConfigOne;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;


@Configuration
public class MyBatisConfigOne {

	// 配置数据源
	@Primary
	@Bean(name = "dataSourceOne")
	public DataSource dataSource(@Qualifier("dBConfigOne") DBConfigOne config) throws SQLException {
		MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
		mysqlXaDataSource.setUrl(config.getUrl());
		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
		mysqlXaDataSource.setPassword(config.getPassword());
		mysqlXaDataSource.setUser(config.getUsername());
		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
 
		AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(mysqlXaDataSource);
		xaDataSource.setUniqueResourceName("dataSourceOne");
 
		xaDataSource.setMinPoolSize(config.getMinPoolSize());
		xaDataSource.setMaxPoolSize(config.getMaxPoolSize());
		xaDataSource.setMaxLifetime(config.getMaxLifetime());
		xaDataSource.setBorrowConnectionTimeout(config.getBorrowConnectionTimeout());
		xaDataSource.setLoginTimeout(config.getLoginTimeout());
		xaDataSource.setMaintenanceInterval(config.getMaintenanceInterval());
		xaDataSource.setMaxIdleTime(config.getMaxIdleTime());
		xaDataSource.setTestQuery(config.getTestQuery());
		return xaDataSource;
	}
	@Bean(name = "sqlSessionFactoryOne")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSourceOne") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		// 配置mapper的扫描，找到所有的mapper.xml映射文件
        Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapperOne/*.xml");
        bean.setMapperLocations(resources);

		return bean.getObject();
	}
	
	@Bean(name = "sqlSessionTemplateOne")
	public SqlSessionTemplate sqlSessionTemplate(
			@Qualifier("sqlSessionFactoryOne") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	@Configuration
	@AutoConfigureAfter(MyBatisConfigOne.class)
	public static class MyBatisMapperScannerConfig {

		@Bean
		public MapperScannerConfigurer mapperScannerConfig() {
			MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
			mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryOne");
			mapperScannerConfigurer.setBasePackage("com.side.movement.basketball.mapperOne");
			//配置通用mappers
			Properties properties = new Properties();
			properties.setProperty("notEmpty", "false");
			properties.setProperty("IDENTITY", "MYSQL");
			mapperScannerConfigurer.setProperties(properties);

			return mapperScannerConfigurer;
		}
	}


}
