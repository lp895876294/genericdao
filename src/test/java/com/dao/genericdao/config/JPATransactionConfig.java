package com.dao.genericdao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement(proxyTargetClass=true)
public class JPATransactionConfig {
	/**
	 * 创建JPA事务管理器，事务管理器的类名称为：transactionManager
	 * @return
	 */
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager() ;
		jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
		return jpaTransactionManager ;
	}
}
