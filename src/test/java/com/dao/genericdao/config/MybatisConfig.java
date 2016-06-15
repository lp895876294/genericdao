package com.dao.genericdao.config;

import com.dao.genericdao.mybatis.annotation.MybatisRepository;
import com.dao.genericdao.mybatis.meta.JPAObjectWrapperFactory;
import com.dao.genericdao.mybatis.plugins.page.support.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MybatisConfig {
	/**
	 * mybatis配置文件路径
	 */
	public static final String PROPERTY_MYBATIS_CONFIG = "mybatis.config";
	/**
	 * 方言类
	 */
	public static final String PROPERTY_MYBATIS_DIALECT = "mybatis.dialect";
	/**
	 * 实体别名包
	 */
	public static final String PROPERTY_MYBATIS_ALIAS_PACKAGE = "mybatis.alias.package";
	/**
	 * dao接口所在的包
	 */
	public static final String PROPERTY_MYBATIS_MAPPER_PACKAGE = "mybatis.mapper.package";
	/**
	 * 映射文件所在路径
	 */
	public static final String PROPERTY_MYBATIS_MAPPER_LOCATION = "mybatis.mapper.location";
	
	@Bean
	public SqlSessionFactoryBean sessionFactoryBean(DataSource dataSource , Environment environment) throws IOException{
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean() ;
		sqlSessionFactoryBean.setDataSource(dataSource);
		//别名包
		sqlSessionFactoryBean.setTypeAliasesPackage(environment.getRequiredProperty(PROPERTY_MYBATIS_ALIAS_PACKAGE));
		
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver() ;
		//获取mybatis映射文件路径，classpath:mybatis/mybatis-config.xml
		if(environment.containsProperty(PROPERTY_MYBATIS_CONFIG)){
			sqlSessionFactoryBean.setConfigLocation(resolver.getResource(environment.getRequiredProperty(PROPERTY_MYBATIS_CONFIG)));
		}
		//获取mybatis映射文件路径，例：classpath*:com/framework/sample/mapper/*Mapper.xml
		sqlSessionFactoryBean.setMapperLocations(resolver.getResources(environment.getRequiredProperty(PROPERTY_MYBATIS_MAPPER_LOCATION)));

		//开启后将在启动时检查设定的parameterMap,resultMap是否存在，是否合法
		sqlSessionFactoryBean.setFailFast(true) ;

		//存储mybatis的插件
		List<Interceptor> interceptorList = new ArrayList<Interceptor>() ;
		//初始化mybatis的插件
		initPlugins(interceptorList , environment) ;
		//设置mybatis插件
		sqlSessionFactoryBean.setPlugins(interceptorList.toArray(new Interceptor[]{}));

		//创建自定义的的Configuration
		Configuration configuration = new Configuration() ;
		//设置ObjectWrapperFactory，在其中映射mybatis查询结果和jpa实体
		configuration.setObjectWrapperFactory(new JPAObjectWrapperFactory());
		sqlSessionFactoryBean.setConfiguration(configuration);

		return sqlSessionFactoryBean ;
	}
	
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer(Environment environment){
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer() ;
		//由配置文件中获取mybatis映射DAO的路径
		mapperScannerConfigurer.setBasePackage(environment.getRequiredProperty(PROPERTY_MYBATIS_MAPPER_PACKAGE));
		//mybatis的接口必须集成mybatis接口的标识接口
		mapperScannerConfigurer.setAnnotationClass(MybatisRepository.class);
		
		return mapperScannerConfigurer ;
	}

	/**
	 * 初始化分页插件
	 * @param interceptorList
	 * @param environment
     */
	protected void initPagePlugin(List<Interceptor> interceptorList , Environment environment){
		//初始化mybatis的分页插件
		PageInterceptor pageHelper = new PageInterceptor() ;
		Properties properties = new Properties() ;
		properties.put("dialect", environment.getProperty(PROPERTY_MYBATIS_DIALECT , "mysql")) ;
		//不进行合理化查询优化
		properties.put("reasonable", "false") ;
		pageHelper.setProperties(properties);
		//添加分页插件
		interceptorList.add(pageHelper) ;
	}

	/**
	 * 初始化mybatis的插件
	 */
	protected void initPlugins(List<Interceptor> interceptorList, Environment environment) {
		initPagePlugin(interceptorList , environment) ;
	}

}
