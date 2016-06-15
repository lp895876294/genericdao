package com.dao.genericdao.dao;

import com.dao.genericdao.annotation.MybatisQuery;
import com.dao.genericdao.entity.TmpUser;
import com.dao.genericdao.jpa.GenericJpaRepository;
import com.dao.genericdao.mybatis.plugins.page.MybatisPage;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by liupeng on 2016-6-15.
 */
public interface TmpUserDao extends GenericJpaRepository<TmpUser,String> {

    @MybatisQuery
    List<TmpUser> findAll(@Param("param") Map<String,Object> searchParam) ;

    @MybatisQuery
    MybatisPage<TmpUser> findAll(@Param("param") Map<String,Object> searchParam , Pageable pageable) ;

}
