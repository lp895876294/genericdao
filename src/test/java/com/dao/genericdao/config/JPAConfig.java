package com.dao.genericdao.config;

import com.dao.genericdao.jpa.GenericJpaRepositoryFactoryBean;
import com.dao.genericdao.util.JPAUtil;
import org.hibernate.cfg.AvailableSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@EnableJpaRepositories(basePackages={"com.dao.genericdao"} ,
	repositoryFactoryBeanClass=GenericJpaRepositoryFactoryBean.class)
@Configuration
public class JPAConfig {
	
	private static Logger log = LoggerFactory.getLogger(JPAConfig.class) ;
	
	/**
	 * jpa实体扫描的根目录
	 */
	public static final String PROPERTY_NAME_JPA_ENTITY_PACKAGE = "jpa.entity.package";
	
	public static final String DEFAULT_CACHE_REGION_PREFIX = "secondlevelcache:hibernate" ;
	
	@Autowired
	private Environment environment ;
	
	@Bean
	public HibernateJpaVendorAdapter jpaVendorAdapter(){
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter() ;
		return jpaVendorAdapter ;
	}
	
	/**
	 * 初始化jpa实体工厂
	 * @param dataSource 数据源
	 * @param hibernateJpaVendorAdapter
	 * @return
	 */
	@Bean(name="entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(DataSource dataSource , HibernateJpaVendorAdapter hibernateJpaVendorAdapter){
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean() ;
		
		entityManagerFactory.setDataSource(dataSource) ;
		//获取需要检测的包
		String[] packages = environment.getRequiredProperty(PROPERTY_NAME_JPA_ENTITY_PACKAGE).split("[;,]") ;
		
		entityManagerFactory.setPackagesToScan(packages);
		
		entityManagerFactory.setJpaVendorAdapter(hibernateJpaVendorAdapter);
		
		//jpa属性
		Properties jpaProperties = new Properties() ;
		//默认的schema
		setHibernateProperty(AvailableSettings.DEFAULT_SCHEMA ,jpaProperties,null) ;
		//默认不显示sql，建议使用log的方式打印sql日志及参数：
		//<logger name="org.hibernate.SQL" level="DEBUG"></logger>
		//<logger name="org.hibernate.type" level="TRACE"></logger>
		setHibernateProperty(AvailableSettings.SHOW_SQL,jpaProperties,false) ;
		//默认不对sql进行格式化
		setHibernateProperty(AvailableSettings.FORMAT_SQL,jpaProperties,false) ;

		setHibernateProperty(AvailableSettings.HBM2DDL_AUTO,jpaProperties,"update") ;
		
		setHibernateProperty(AvailableSettings.GENERATE_STATISTICS,jpaProperties,false) ;
		
		setHibernateProperty(AvailableSettings.DIALECT , jpaProperties , JPAUtil.getDialect(dataSource)) ;
		
		setHibernateProperty(AvailableSettings.AUTOCOMMIT,jpaProperties,false) ;

		//使用二级查询缓存，默认不启用
		setHibernateProperty(AvailableSettings.USE_SECOND_LEVEL_CACHE,jpaProperties , false) ;

		//判断是否启用二级缓存
		if(environment.containsProperty(AvailableSettings.USE_SECOND_LEVEL_CACHE)){
			log.warn("是否启用二级缓存："+environment.getProperty(AvailableSettings.USE_SECOND_LEVEL_CACHE, Boolean.class));
			if(environment.getProperty(AvailableSettings.USE_SECOND_LEVEL_CACHE, Boolean.class)){
				//使用单条记录的查询缓存
//				setHibernateProperty(AvailableSettings.USE_QUERY_CACHE,jpaProperties , true) ;
//				//使用集合的查询缓存
//				setHibernateProperty(AvailableSettings.USE_STRUCTURED_CACHE , jpaProperties , true) ;
//				//二级缓存的配置，包括redis连接工厂、cache超时时间等
//				RedisCacheConfig.redisConnectionFactory = redisConnectionFactory ;
//				//二级缓存超时时间，默认为1800s
//				RedisCacheConfig.cacheTimeout = environment.getProperty("hibernate.cache.timeout", Integer.class, 1800) ;
//				//设置RedisQueryCache的redisCacheConfig属性
//				RedisQueryCache.redisCacheConfig = new RedisCacheConfig() ;
//				//使用自定义的QueryCacheFactory，hibernate默认为StandardQueryCacheFactory
//				setHibernateProperty(AvailableSettings.QUERY_CACHE_FACTORY , jpaProperties , RedisQueryCacheFactory.class.getName()) ;
//				//设置二级缓存的配置类
//				SingletonRedisRegionFactory.redisCacheConfig = new RedisCacheConfig() ;
//				//设置cache需要的region工厂
//				setHibernateProperty(AvailableSettings.CACHE_REGION_FACTORY , jpaProperties , SingletonRedisRegionFactory.class.getName()) ;
//				//缓存前缀名称
//				setHibernateProperty(AvailableSettings.CACHE_REGION_PREFIX , jpaProperties , DEFAULT_CACHE_REGION_PREFIX) ;
//
//				setHibernateProperty(AvailableSettings.GENERATE_STATISTICS , jpaProperties , false) ;
			}
		}
		
		entityManagerFactory.setJpaProperties(jpaProperties) ;
		
		return entityManagerFactory ;
	}
	
	private void setHibernateProperty(String propertyName , Properties jpaProperties , Object defaultValue){
		if(environment.containsProperty(propertyName)){
			jpaProperties.put(propertyName, environment.getProperty(propertyName)) ;
		}else if(defaultValue!=null){
			jpaProperties.put(propertyName, defaultValue) ;
		}
	}
}
