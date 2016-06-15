package com.dao.genericdao.jpa;

import com.dao.genericdao.dao.BaseDataRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 扩展JpaRepository，添加对update的支持
 */
@NoRepositoryBean
public interface GenericJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID>,
		JpaSpecificationExecutor<T> , BaseDataRepository<T,ID> {
	/**
	 * 统计加和运算
	 * @param spec 查询条件
	 * @param fieldName 字段名称
	 * @param resultType 返回值类型
	 * @return
	 */
	public <S extends Number> S sum(String fieldName, Class<S> resultType, Specification<T> spec) ;

	/**
	 * 查询多个字段
	 * @param fields 查询字段数组
	 * @param spec 查询条件
     * @return 返回list，每个元素为一个map对象，存储字段名称和字段值的映射
     */
	public List<Map<String, Object>> queryMultiFields(String[] fields, Specification<T> spec) ;

	/**
	 * 查询单个字段
	 * @param field
	 * @param spec
     * @return 返回待查询字段的list对象
     */
	public List<String> querySingleFields(String field, Specification<T> spec) ;

	/**
	 * 获取当前dao对应的实体管理器
	 * @return
     */
	public EntityManager getEntityManager() ;
}
