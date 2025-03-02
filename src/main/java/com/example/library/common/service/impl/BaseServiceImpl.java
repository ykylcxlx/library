package com.example.library.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.library.common.pojo.entity.BaseEntity;
import com.example.library.common.service.BaseService;
import com.example.library.etc.ServiceException;
import com.example.library.util.MpWrapperUtil;

import java.io.Serializable;

/**
 * @author WangYi
 */
public class BaseServiceImpl<T extends BaseEntity, R extends BaseMapper<T>> extends ServiceImpl<R, T> implements BaseService<T> {
    @Override
    public T getById(Serializable id) {
        T target = super.getById(id);
        if (target == null) {
            throw new ServiceException("目标数据不存在");
        }
        return target;
    }

    @Override
    public T getOne(T entity) {
        return getOne(entity, false);
    }

    @Override
    public T getOne(T entity, boolean nullable) {
        return getOne(entity, true, nullable);
    }

    @Override
    public T getOne(T entity, boolean matchAll, boolean nullable) {
        T targetEntity;
        QueryWrapper<T> wrapper;
        if (matchAll) {
            wrapper = new QueryWrapper<>(entity);
        } else {
            wrapper = MpWrapperUtil.getQueryWrapper(entity);
        }
        targetEntity = super.getOne(wrapper);
        if (targetEntity == null && ! nullable) {
            throw new ServiceException("目标数据不存在");
        }
        return targetEntity;
    }

    @Override
    public boolean exists(Long id) {
        return exists(id, true);
    }

    @Override
    public boolean exists(Long id, boolean throwEx) {
        LambdaQueryWrapper<T> wrapper = Wrappers.lambdaQuery();
        wrapper.setEntityClass(getEntityClass());
        wrapper.eq(T::getId, id);
        boolean res = super.exists(wrapper);
        if (!res && throwEx) {
            throw new ServiceException("目标数据不存在");
        }
        return res;
    }

    @Override
    public boolean exists(T entity) {
        return exists(entity, true);
    }

    @Override
    public boolean exists(T entity, boolean matchAll) {
        return exists(entity, matchAll, true);
    }

    @Override
    public boolean exists(T entity, boolean matchAll, boolean throwable) {
        Wrapper<T> wrapper = matchAll? Wrappers.lambdaQuery(entity) : MpWrapperUtil.getQueryWrapper(entity, MpWrapperUtil.WrapperType.ANY_MATCH);
        boolean exist = super.exists(wrapper);
        if (!exist && throwable) {
            throw new ServiceException("目标数据不存在");
        }
        return exist;
    }

    @Override
    public IPage<T> page(IPage<T> page, T entity, Integer queryType) {
        QueryWrapper<T> wrapper = MpWrapperUtil.getQueryWrapper(entity, queryType);
        wrapper.orderByDesc("create_time");
        return super.page(page, wrapper);
    }
}
