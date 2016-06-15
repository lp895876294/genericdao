package com.dao.genericdao.jpa.specification;

import org.springframework.data.jpa.domain.Specification;

/**
 * 设置地否可以缓存。主要针对非ID的查询方式的缓存
 */
public interface CacheableSpecification<T> extends Specification<T> {
	/**
	 * 是否可以缓存
	 * @return
	 */
	public boolean isCacheable() ;
}
