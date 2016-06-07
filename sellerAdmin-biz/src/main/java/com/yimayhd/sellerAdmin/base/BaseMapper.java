package com.yimayhd.sellerAdmin.base;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * @author czf
 */
public interface BaseMapper<T extends BaseModel> {

	long getCount(@Param("entity") T entity) throws Exception;

	List<T> getList(PageQuery<T> vo) throws Exception;

	void modify(T entity) throws Exception;

	long add(T entity) throws Exception;

	T getById(long id) throws Exception;

	void delete(long id) throws Exception;
}
