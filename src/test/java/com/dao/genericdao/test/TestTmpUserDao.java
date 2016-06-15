package com.dao.genericdao.test;

import com.alibaba.fastjson.JSON;
import com.dao.genericdao.base.BaseTest;
import com.dao.genericdao.dao.TmpUserDao;
import com.dao.genericdao.entity.TmpUser;
import com.dao.genericdao.mybatis.plugins.page.MybatisPageRequest;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.Map;
import java.util.UUID;

@Slf4j
public class TestTmpUserDao extends BaseTest {

    @Autowired
    private TmpUserDao tmpUserDao ;


    public void testSave(){
        TmpUser tmpUser = new TmpUser() ;
        tmpUser.setUsername("lp,"+ UUID.randomUUID().toString()) ;
        tmpUser.setPhoto("my photo path") ;
        tmpUserDao.save(tmpUser) ;
    }

    @Test
    public void queryAll(){

        Map<String,Object> searchParam = Maps.newHashMap() ;

        Page<TmpUser> result = tmpUserDao.findAll(searchParam , new MybatisPageRequest(0,1)) ;

        log.info("result->{}", JSON.toJSONString(result , true));

    }

}
