package com.dao.genericdao.mybatis.meta;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;

import javax.persistence.Entity;

/**
 * 只对JPA对象封装集成
 */
@Slf4j
public class JPAObjectWrapperFactory extends DefaultObjectWrapperFactory {

    @Override
    public boolean hasWrapperFor(Object object) {
        if(object!=null && object.getClass().getAnnotation(Entity.class)!=null){
            return true ;
        }
        return false;
    }

    @Override
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        log.info("返回{}的objectWrapper对象。",object.getClass().getName());

        ObjectWrapper objectWrapper = new JpaBeanWrapper(metaObject , object) ;

        return objectWrapper ;
    }


}
