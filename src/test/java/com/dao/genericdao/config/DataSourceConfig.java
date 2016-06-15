package com.dao.genericdao.config;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

import javax.sql.DataSource;

/**
 * 数据库连接池通用配置信息，存储数据库连接池通用的配置参数等。
 */
@Slf4j
public abstract class DataSourceConfig {
	//数据库命名空间
	public static final String PROPERTY_NAME_DB_PREFIX = "jdbc.prefix";
	
	public static final String PROPERTY_NAME_JDBC_DRIVER = "jdbc.driver";
	
	public static final String PROPERTY_NAME_JDBC_URL = "jdbc.url" ;
	
	public static final String PROPERTY_NAME_JDBC_USERNAME = "jdbc.username" ;
	
	public static final String PROPERTY_NAME_JDBC_PASSWORD = "jdbc.password" ;
	
	public static final String PROPERTY_NAME_JDBC_POOL_MAXACTIVE = "jdbc.pool.maxActive" ;
	
	public static final String PROPERTY_NAME_JDBC_POOL_MINIDLE = "jdbc.pool.maxIdle" ;
	
	public static final String PROPERTY_NAME_VALIDATION_QUERY = "jdbc.validation.query" ;
	
	public static final String PROPERTY_NAME_VALIDATION_INTERVAL = "jdbc.validation.interval" ;
	
	/**
	 * 获取数据库连接池，支持单源和多源数据库连接
	 */
	@Bean(destroyMethod="close")
	public DataSource dataSource(Environment environment) {
		String[] jdbcPrefixArray = getJDBCPrefix(environment) ;
		DataSource dataSource = null ;

		log.info("JDBC前缀符为 "+ Joiner.on(",").join(jdbcPrefixArray));

		Assert.isTrue(jdbcPrefixArray.length<2 , "不支持多数据源配置。");

		if(jdbcPrefixArray==null || jdbcPrefixArray.length<1){
			dataSource = buildDataSource(environment, "") ;
		}else if(jdbcPrefixArray.length==1){
			dataSource = buildDataSource(environment, jdbcPrefixArray[0]+".") ;
		}
		
		return dataSource;
	}
	
	/**
	 * 获取数据库连接池前缀，默认返回空
	 * @param environment
	 * @return
	 */
	public String[] getJDBCPrefix(Environment environment){
		//获取jdbc前缀，根据jdbc前缀获取不同的连接池信息
		if(StringUtils.isNotBlank(environment.getProperty(PROPERTY_NAME_DB_PREFIX))){
			return StringUtils.split(environment.getProperty(PROPERTY_NAME_DB_PREFIX), ",") ;
		}else{
			return new String[]{} ;
		}
	}
	/**
	 * 构建数据库连接池
	 * @param environment
	 * @param propertyPrefix
	 * @return
	 */
	public abstract DataSource buildDataSource(Environment environment , String propertyPrefix) ;

}
