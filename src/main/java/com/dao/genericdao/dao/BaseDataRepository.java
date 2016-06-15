package com.dao.genericdao.dao;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 基本spring data接口，包含基本的持久化操作
 * @param <T> 实体类
 * @param <ID> 主键ID类型，建议使用String，设置为UUID
 */
@NoRepositoryBean
public interface BaseDataRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T,ID> {
    /**
     * 更新实体对象
     * @param entity 实体
     * @param updateFieldsName 更新字段名称
     */
    public T update(T entity , Collection<String> updateFieldsName) ;

    /**
     * 更新实体对象，更新所有的属性
     * @param entity 实体
     */
    public T update(T entity) ;

    /**
     * 批量更新实体对象
     * @param entityList 实体集合
     * @param updateFieldsName 更新字段名称
     */
    public List<T> update(List<T> entityList , Collection<String> updateFieldsName) ;

    /**
     * 批量更新实体对象
     * @param entityList 实体
     */
    public List<T> update(List<T> entityList) ;

    /**
     * 获取进行操作的领域类
     */
    public Class<T> getDomainClass() ;

}
