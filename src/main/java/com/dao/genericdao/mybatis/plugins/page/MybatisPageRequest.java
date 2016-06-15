package com.dao.genericdao.mybatis.plugins.page;

import com.dao.genericdao.mybatis.plugins.page.support.PageInterceptor;
import org.springframework.data.domain.PageRequest;

/**
 * mybatis分页请求，分页号由0开始
 * 通过通用的接口PageRequest将分页号等参数暂存起来。
 * 在后续的mybatis接口中并没有直接使用PageRequest类
 */
public class MybatisPageRequest extends PageRequest {
	private static final long serialVersionUID = 1L;
	/**
	 * 创建mybatis分页请求，分页号（pageNum）由0开始
	 * @param pageNum
	 * @param pageSize
	 */
	public MybatisPageRequest(int pageNum, int pageSize) {
		super(pageNum, pageSize);
		//设置分页信息
		PageInterceptor.startPage(pageNum+1, pageSize) ;
	}
	
	public MybatisPageRequest() {
		super(0, 1);
	}
}
