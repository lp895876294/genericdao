package com.dao.genericdao.mybatis.plugins.page;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * mybatis的分页接口，必须实现List。
 * Mybatis的接口会匹配接口方法返回值的类型和返回记录的条数，
 * 如果是返回值有多条，函数返回值类型必须为集合接口。
 * @param <T>
 */
public interface MybatisPage<T> extends Page<T>, List<T> {
	
}
