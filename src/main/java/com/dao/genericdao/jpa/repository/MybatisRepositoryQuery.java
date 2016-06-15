package com.dao.genericdao.jpa.repository;

import com.google.common.collect.Maps;
import org.apache.ibatis.mapping.ResultMap;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.QueryMethod;
import org.springframework.data.repository.query.RepositoryQuery;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

/**
 * mybatis查询执行器
 */
public class MybatisRepositoryQuery implements RepositoryQuery {

    private static Logger log = LoggerFactory.getLogger(MybatisRepositoryQuery.class) ;

    /**
     * 存储dao接口和mybatis中mapper代理的映射
     */
    private static Map<String,Object> mybatisMapperMap = Maps.newHashMap() ;

    private static Map<String,ResultMap> entityResultMap = Maps.newHashMap() ;

    private Method method ;

    private RepositoryMetadata repositoryMetadata ;

    private SqlSessionTemplate sqlSessionTemplate ;

    public MybatisRepositoryQuery(SqlSessionTemplate sqlSessionTemplate ,
                                  Method method, RepositoryMetadata repositoryMetadata){
        this.sqlSessionTemplate = sqlSessionTemplate ;
        this.method = method ;
        this.repositoryMetadata = repositoryMetadata ;

        log.info("{}的领域类{}",repositoryMetadata.getRepositoryInterface().getName() , repositoryMetadata.getDomainType() );

        if(!mybatisMapperMap.containsKey(getMapperKey())){
            Object mapper = sqlSessionTemplate.getMapper(repositoryMetadata.getRepositoryInterface()) ;
            mybatisMapperMap.put(getMapperKey() , mapper) ;
        }

    }

    @Override
    public Object execute(Object[] parameters) {

        log.info("执行{}.{}，参数为{}" , repositoryMetadata.getRepositoryInterface().getName() ,
                method.getName() , parameters!=null?Arrays.toString(parameters):"") ;

        Object result = null ;
        try {
            Object mapper = mybatisMapperMap.get(getMapperKey()) ;
            Assert.isTrue(mapper!=null , repositoryMetadata.getRepositoryInterface().getName()+"对应的Mapper为null");
            if(mapper!=null){
                result = method.invoke(mapper , parameters) ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result ;
    }

    @Override
    public QueryMethod getQueryMethod() {
        return new QueryMethod(method , repositoryMetadata) ;
    }

    /**
     * 获取mybatis中mapper的key
     * @return
     */
    public String getMapperKey(){
        return repositoryMetadata.getRepositoryInterface().getName() ;
    }

    public Method getMethod() {
        return method;
    }

    public RepositoryMetadata getRepositoryMetadata() {
        return repositoryMetadata;
    }

    public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

}
