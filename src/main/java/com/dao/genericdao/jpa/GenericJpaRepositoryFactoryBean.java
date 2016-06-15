package com.dao.genericdao.jpa;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.io.Serializable;
/**
 * 自定义的JpaRepositoryFactoryBean类，用于生成CustomJpaRepositoryFactory
 */
public class GenericJpaRepositoryFactoryBean<T extends Repository<S, ID>, S, ID extends Serializable> extends
		JpaRepositoryFactoryBean<T, S, ID> {

	private static final Logger log = LoggerFactory.getLogger(GenericJpaRepositoryFactoryBean.class) ;

	private BeanFactory beanFactory ;

	private static SqlSessionTemplate sqlSessionTemplate ;
	
	@Override
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {

		if(sqlSessionTemplate==null){
			Assert.isTrue(beanFactory!=null , "beanFactory不能为null") ;
			SqlSessionFactory sqlSessionFactory = beanFactory.getBean(SqlSessionFactory.class) ;
			Assert.isTrue(sqlSessionFactory!=null , "sqlSessionFactory不能为空") ;
			log.info("由beanFactory中获取SqlSessionFactory，并创建SqlSessionTemplate");
			//创建SqlSessionTemplate，SqlSessionTemplate是线程安全的
			sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory) ;
		}

		return new GenericJpaRepositoryFactory(entityManager , sqlSessionTemplate);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory ;
		super.setBeanFactory(beanFactory) ;
	}

	/**
	 * 获取beanfactory
	 * @return
     */
	public BeanFactory getBeanFactory() {
		return  beanFactory ;
	}

}
