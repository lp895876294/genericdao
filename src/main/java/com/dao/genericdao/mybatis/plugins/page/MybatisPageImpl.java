package com.dao.genericdao.mybatis.plugins.page;

import com.dao.genericdao.mybatis.plugins.page.support.PageHelper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * Mybatis分页接口的实现，在现有分页接口插件PageHelper类上的进一步封装
 * 主要为了兼容spring提供的分页接口Page<T>
 * @author LIUPENG
 * @param <T>
 */
public class MybatisPageImpl<T> extends ArrayList<T> implements MybatisPage<T> {
	
	private PageHelper page ;
	
	public MybatisPageImpl(){
		
	}
	
	public MybatisPageImpl(PageHelper page){
		super(page.getPageSize()) ;
		
		this.page = page ;
		
		this.addAll(page) ;
	}

	@Override
	public int getNumber() {
		return page.getPageNum() ;
	}

	@Override
	public int getSize() {
		return page.getPageSize() ;
	}

	@Override
	public int getNumberOfElements() {
		return this.size();
	}

	@Override
	public List<T> getContent() {
		return this;
	}

	@Override
	public boolean hasContent() {
		return !this.isEmpty();
	}

	@Override
	public Sort getSort() {
		return null;
	}

	@Override
	public boolean isFirst() {
		return page.getPageNum()==1;
	}

	@Override
	public boolean isLast() {
		return page.getPageNum() == page.getPages() ;
	}

	@Override
	public boolean hasNext() {
		return page.getPages()>page.getPageNum();
	}

	@Override
	public boolean hasPrevious() {
		return page.getPageNum()>1;
	}

	@Override
	public Pageable nextPageable() {
		return this.hasNext() ? new PageRequest(page.getPageNum()+1, page.getPageSize()) : null ;
	}

	@Override
	public Pageable previousPageable() {
		return this.hasPrevious() ? new PageRequest(page.getPageNum()-1, page.getPageSize()) : null ;
	}

	@Override
	public int getTotalPages() {
		return page.getPages();
	}

	@Override
	public long getTotalElements() {
		return page.getTotal();
	}

	@Override
	public <S> Page<S> map(Converter<? super T, ? extends S> converter) {
		return null;
	}

}
