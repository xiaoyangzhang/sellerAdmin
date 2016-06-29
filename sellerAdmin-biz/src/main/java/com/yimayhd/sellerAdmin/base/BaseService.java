package com.yimayhd.sellerAdmin.base;

import java.util.List;

public interface BaseService<T extends BaseModel> {

	/**
	 * 统计数目
	 *
	 * @param entity
	 *            条件
	 * @throws Exception
	 */
	long getCount(T entity) throws Exception;

	/**
	 * 查询
	 *
	 * @param vo
	 *            查询条件及其他参数
	 * @throws Exception
	 */
	List<T> getList(PageQuery<T> vo) throws Exception;

	/**
	 * 修改
	 *
	 * @param entity
	 *            需要修改的字段和主键
	 * @throws Exception
	 */
	void modify(T entity) throws Exception;

	/**
	 * 添加
	 * 
	 * @return 主键
	 * @param entity
	 *            数据实体
	 * @throws Exception
	 */
	T add(T entity) throws Exception;

	/**
	 * 根据主键获取
	 *
	 * @param id
	 *            主键long类型的
	 * @throws Exception
	 */
	T getById(long id) throws Exception;

	/**
	 * 删除
	 *
	 * @param id
	 *            主键long类型的
	 * @throws Exception
	 */
	void delete(long id) throws Exception;
}
