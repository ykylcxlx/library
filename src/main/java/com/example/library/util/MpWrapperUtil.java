package com.example.library.util;

import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.library.annotations.EqInCondition;
import com.example.library.etc.ServiceException;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;

/**
 * @author WY
 **/
public class MpWrapperUtil {
    public enum WrapperType {
        /**
         * 数据库对象 模糊查询条件 eg: like '%xxx%' and like '%xxx%'
         */
        CONTAINS,

        /**
         * 数据库对象 属性或查询条件 eg: = ? or = ?
         */
        ANY_MATCH
    }

    public static <T> QueryWrapper<T> getQueryWrapper(T entity) {
        return getQueryWrapper(entity, WrapperType.CONTAINS.ordinal());
    }

    public static <T> QueryWrapper<T> getQueryWrapper(T entity, Integer queryType) {
        if (queryType == null) {
            return getQueryWrapper(entity, WrapperType.CONTAINS);
        }
        for (WrapperType value : WrapperType.values()) {
            if (value.ordinal() == queryType) {
                return getQueryWrapper(entity, value);
            }
        }
        throw new ServiceException("条件匹配类型");
    }

    public static <T> QueryWrapper<T> getQueryWrapper(T entity, WrapperType wrapperType) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        if (ObjectUtils.isEmpty(entity)) {
            return wrapper;
        }
        Field[] fields = ReflectUtil.getFields(entity.getClass());
        for (Field field : fields) {
            Object fieldVal = ReflectUtil.getFieldValue(entity, field);
            if (ObjectUtils.isEmpty(fieldVal)) {
                continue;
            }

            if (wrapperType.equals(WrapperType.ANY_MATCH)) {
                wrapper.or().eq(StringUtils.camelToUnderline(field.getName()), fieldVal);
            } else if (wrapperType.equals(WrapperType.CONTAINS)) {
                //若当前属性为主键或具备相应注解，按Eq形式作为查询条件否则以Like形式作为查询条件
                if (field.isAnnotationPresent(TableId.class) || field.isAnnotationPresent(EqInCondition.class)) {
                    wrapper.eq(StringUtils.camelToUnderline(field.getName()), fieldVal);
                    continue;
                }
                wrapper.like(StringUtils.camelToUnderline(field.getName()), fieldVal);
            }
        }
        return wrapper;
    }
}
