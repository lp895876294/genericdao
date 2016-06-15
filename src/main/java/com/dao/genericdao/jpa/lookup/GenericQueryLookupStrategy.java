package com.dao.genericdao.jpa.lookup;

import com.dao.genericdao.annotation.MybatisQuery;
import com.dao.genericdao.jpa.repository.MybatisRepositoryQuery;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.provider.QueryExtractor;
import org.springframework.data.jpa.repository.query.JpaQueryLookupStrategy;
import org.springframework.data.repository.core.NamedQueries;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.EvaluationContextProvider;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.RepositoryQuery;

import javax.persistence.EntityManager;
import java.lang.reflect.Method;

/**
 * 方法查询策略。主要检测mybatis查询方法
 */
public class GenericQueryLookupStrategy implements QueryLookupStrategy {

    private static Logger log = LoggerFactory.getLogger(GenericQueryLookupStrategy.class) ;

    private final EntityManager entityManager;

    private final SqlSessionTemplate sqlSessionTemplate ;

    private QueryLookupStrategy jpaQueryLookupStrategy;

    private QueryExtractor extractor;

    public GenericQueryLookupStrategy(EntityManager entityManager, SqlSessionTemplate sqlSessionTemplate  ,
                                      Key key, QueryExtractor extractor, EvaluationContextProvider evaluationContextProvider) {
        this.jpaQueryLookupStrategy = JpaQueryLookupStrategy.create(entityManager, key, extractor, evaluationContextProvider);
        this.extractor = extractor;
        this.entityManager = entityManager;
        this.sqlSessionTemplate = sqlSessionTemplate ;
    }

    public static QueryLookupStrategy create(EntityManager entityManager, SqlSessionTemplate sqlSessionTemplate  ,
                                             Key key, QueryExtractor extractor, EvaluationContextProvider evaluationContextProvider) {
        return new GenericQueryLookupStrategy(entityManager , sqlSessionTemplate ,
                key , extractor , evaluationContextProvider);
    }

    @Override
    public RepositoryQuery resolveQuery(Method method, RepositoryMetadata metadata, NamedQueries namedQueries) {
        if (method.getAnnotation(MybatisQuery.class) != null) {
            log.info(metadata.getRepositoryInterface().getName()+"."+method.getName()+" 为mybatis方法。 ");
            return new MybatisRepositoryQuery(sqlSessionTemplate , method , metadata) ;
        } else {
            return jpaQueryLookupStrategy.resolveQuery(method, metadata, namedQueries);
        }
    }
}
