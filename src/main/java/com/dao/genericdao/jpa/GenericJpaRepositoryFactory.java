package com.dao.genericdao.jpa;

import com.dao.genericdao.jpa.impl.GenericJpaRepositoryImpl;
import com.dao.genericdao.jpa.lookup.GenericQueryLookupStrategy;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.provider.QueryExtractor;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.EvaluationContextProvider;
import org.springframework.data.repository.query.QueryLookupStrategy;

import javax.persistence.EntityManager;

/**
 * 自定义的CustomJpaRepositoryFactory，用于生成CustomJpaRepository
 */
@Slf4j
public class GenericJpaRepositoryFactory extends JpaRepositoryFactory {

	private EntityManager entityManager;

	//mybatis的操作
	private SqlSessionTemplate sqlSessionTemplate ;

	private final QueryExtractor extractor ;
	
	public GenericJpaRepositoryFactory(EntityManager entityManager , SqlSessionTemplate sqlSessionTemplate) {
		super(entityManager) ;
		//设置当前类的实体管理器
		this.entityManager = entityManager ;
		//设置sqlSessionTemplate，线程安全
		this.sqlSessionTemplate = sqlSessionTemplate ;

		this.extractor = PersistenceProvider.fromEntityManager(entityManager);
	}

//	@Override
//	protected <T, ID extends Serializable> SimpleJpaRepository<?, ?> getTargetRepository(
//			RepositoryInformation information, EntityManager entityManager) {
//		log.info("创建实体"+information.getDomainType().getName()+" JPA DAO类："+information.getRepositoryBaseClass().getName()) ;
//
//		JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(information.getDomainType()) ;
//
//		//为构造方法添加参数
//		return getTargetRepositoryViaReflection(information, entityInformation, entityManager , true);
//	}

	@Override
	protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
		//使用自定义的CustomJpaRepositoryImpl
		return GenericJpaRepositoryImpl.class;
	}

	@Override
	protected QueryLookupStrategy getQueryLookupStrategy(QueryLookupStrategy.Key key, EvaluationContextProvider evaluationContextProvider) {
		QueryLookupStrategy queryLookupStrategy = GenericQueryLookupStrategy.create(entityManager ,
				sqlSessionTemplate , key , extractor , evaluationContextProvider) ;

		return queryLookupStrategy  ;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

}
