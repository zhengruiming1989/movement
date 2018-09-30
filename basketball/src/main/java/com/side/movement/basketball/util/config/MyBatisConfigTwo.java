package com.side.movement.basketball.util.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import com.side.movement.basketball.util.db.DBConfigTwo;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
 
@Configuration
@MapperScan(basePackages = "com.side.movement.basketball.mapperTwo", sqlSessionTemplateRef = "sqlSessionTemplateTwo")
public class MyBatisConfigTwo {
	
	// 配置数据源
	@Bean(name = "dataSourceTwo")
	public DataSource dataSource(@Qualifier("dBConfigTwo") DBConfigTwo config) throws SQLException {
		MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
		mysqlXaDataSource.setUrl(config.getUrl());
		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
		mysqlXaDataSource.setPassword(config.getPassword());
		mysqlXaDataSource.setUser(config.getUsername());
		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
 
		AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(mysqlXaDataSource);
		xaDataSource.setUniqueResourceName("dataSourceTwo");
 
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
	@Bean(name = "sqlSessionFactoryTwo")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSourceTwo") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		// 配置mapper的扫描，找到所有的mapper.xml映射文件
		Resource[] resources = null;
		try {
			 resources = new PathMatchingResourcePatternResolver()
					.getResources("classpath:mapperTwo/*.xml");
		}catch (Exception e){
			return null;
		}
		bean.setMapperLocations(resources);
		return bean.getObject();
	}
	
	@Bean(name = "sqlSessionTemplateTwo")
	public SqlSessionTemplate sqlSessionTemplate(
			@Qualifier("sqlSessionFactoryTwo") SqlSessionFactory sqlSessionFactory) throws Exception {
		if(sqlSessionFactory==null){
			return null;
		}
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
