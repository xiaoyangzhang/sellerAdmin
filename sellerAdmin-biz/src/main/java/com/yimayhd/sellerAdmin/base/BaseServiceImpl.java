package com.yimayhd.sellerAdmin.base;


import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

public abstract class BaseServiceImpl<T extends BaseModel> implements BaseService<T> {

    private BaseMapper<T> baseMapper;

    @PostConstruct
    protected abstract void initBaseMapper();

    public void setBaseMapper(BaseMapper<T> baseMapper) {
        this.baseMapper = baseMapper;
    }

    @Override
    public long getCount(T entity) throws Exception {
        return baseMapper.getCount(entity);
    }

    @Override
    public List<T> getList(PageQuery<T> vo) throws Exception {
        return baseMapper.getList(vo);
    }

    @Override
    public void modify(T entity) throws Exception {
        baseMapper.modify(entity);
    }

    @Override
    public T add(T entity) throws Exception {
        //entity.setId(UUIDUtil.generateUniqueKey());
        entity.setGmtCreated(new Date());
        baseMapper.add(entity);
        return entity;
    }

    @Override
    public T getById(long id) throws Exception {
        return baseMapper.getById(id);
    }

    @Override
    public void delete(long id) throws Exception {
        baseMapper.delete(id);
    }
}
