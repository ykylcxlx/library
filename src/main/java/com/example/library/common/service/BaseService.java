package com.example.library.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author WangYi
 */
public interface BaseService<T> extends IService<T> {
    /**
     * 条件查询，结果不为空
     * @param entity 查询条件对象
     * @return 实体对象
     */
    T getOne(T entity);

    /**
     * 条件查询，结果可为空
     * @param entity 查询条件对象
     * @param nullable 是否可为空
     * @return 实体对象
     */
    T getOne(T entity, boolean nullable);

    /**
     * 条件查询，条件匹配模式可选，结果可为空
     * @param entity 查询条件对象
     * @param matchAll 是否匹配所有条件
     * @param nullable 是否可为空
     * @return 实体对象
     */
    T getOne(T entity, boolean matchAll, boolean nullable);

    /**
     * 按ID检查数据库对象是否已存在
     * @param id 主键ID
     * @return 是否存在
     */
    boolean exists(Long id);

    /**
     * 按ID检查数据库对象是否已存在
     * @param id 主键ID
     * @param throwEx 是否抛出异常
     * @return
     */
    boolean exists(Long id, boolean throwEx);

    /**
     * 条件查询，检查数据库对象是否已存在
     * @param entity 查询条件对象
     * @return 是否存在
     */
    boolean exists(T entity);

    /**
     * 条件查询，检查数据库对象是否已存在
     * @param entity 查询条件对象
     * @param matchAll 是否匹配所有条件
     * @return 是否存在
     */
    boolean exists(T entity, boolean matchAll);

    /**
     * 条件查询，检查数据库对象是否已存在
     * @param entity 查询条件对象
     * @param matchAll 是否匹配所有条件
     * @param throwable 是否抛出异常
     * @return 是否存在
     */
    boolean exists(T entity, boolean matchAll, boolean throwable);

    /**
     * 分页查询
     * @param page
     * @param entity
     * @param queryType
     * @return
     */
    IPage<T> page(IPage<T> page, T entity, Integer queryType);
}
