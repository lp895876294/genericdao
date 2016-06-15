package com.dao.genericdao.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 *
 //配置druid监控servlet<br>
 StatViewServlet statViewServlet = new StatViewServlet() ;<br>
 ServletRegistration.Dynamic registration = servletContext.addServlet("druidStatView", statViewServlet);<br>
 registration.setLoadOnStartup(1);<br>
 registration.addMapping(new String[]{"/druid/*"});<br>
 registration.setAsyncSupported(isAsyncSupported());<br>
 */
@Configuration
public class DruidDataSourceConfig extends DataSourceConfig {
	/**
	 * 构建alibaba的数据库连接池
	 * @param environment
	 * @param propertyPrefix
	 * @return
	 */
	@Override
	public DataSource buildDataSource(Environment environment , String propertyPrefix){
		DruidDataSource dataSource = new DruidDataSource() ;
		
		dataSource.setDriverClassName(environment.getRequiredProperty(propertyPrefix+PROPERTY_NAME_JDBC_DRIVER));
		dataSource.setUrl(environment.getRequiredProperty(propertyPrefix+PROPERTY_NAME_JDBC_URL));
		dataSource.setUsername(environment.getRequiredProperty(propertyPrefix+PROPERTY_NAME_JDBC_USERNAME));
		dataSource.setPassword(environment.getRequiredProperty(propertyPrefix+PROPERTY_NAME_JDBC_PASSWORD));
		
		//分别设置最大和最小连接数
		if(environment.containsProperty(propertyPrefix+PROPERTY_NAME_JDBC_POOL_MAXACTIVE)){
			dataSource.setMaxActive(environment.getProperty(propertyPrefix+PROPERTY_NAME_JDBC_POOL_MAXACTIVE , Integer.class));
		}
		
		if(environment.containsProperty(propertyPrefix+PROPERTY_NAME_JDBC_POOL_MINIDLE)){
			dataSource.setMinIdle(environment.getProperty(propertyPrefix+PROPERTY_NAME_JDBC_POOL_MINIDLE , Integer.class));
		}
		
		dataSource.setDefaultAutoCommit(false);
		
		//是否需要进行验证
		dataSource.setValidationQuery(environment.getRequiredProperty(propertyPrefix+PROPERTY_NAME_VALIDATION_QUERY));
		//由连接池中获取连接时，需要进行验证
		dataSource.setTestOnBorrow(true) ;
		//归还连接时不需要验证
		dataSource.setTestOnReturn(false) ;
		//连接空闲时进行验证
		dataSource.setTestWhileIdle(true) ;

		//设置datasource监控，只对sql执行状态进行监控
		try {
			dataSource.setFilters("stat") ;
		}catch (Exception e){
			e.printStackTrace() ;
		}

		if(StringUtils.isNotBlank(environment.getProperty(propertyPrefix+PROPERTY_NAME_VALIDATION_INTERVAL))){
			//连接有效的验证时间间隔
			dataSource.setValidationQueryTimeout(environment.getRequiredProperty(propertyPrefix+PROPERTY_NAME_VALIDATION_INTERVAL , Integer.class));
			//连接空闲验证的时间间隔
			dataSource.setTimeBetweenEvictionRunsMillis(environment.getRequiredProperty(propertyPrefix+PROPERTY_NAME_VALIDATION_INTERVAL , Long.class));
		}
		
		return dataSource ;
	}
	
	

}
