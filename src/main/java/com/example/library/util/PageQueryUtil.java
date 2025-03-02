package com.example.library.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.library.common.pojo.model.PageQuery;

/**
 * @author WangYi
 */
public class PageQueryUtil {
    public static <T> IPage<T> getPage() {
        return getPage(null);
    }

    public static <T> IPage<T> getPage(PageQuery pageQuery) {
        if (pageQuery == null) {
            return new Page<>(1, 10);
        }
        return new Page<>(pageQuery.getCurrent() == null? 1 : pageQuery.getCurrent(), pageQuery.getSize() == null? 10 : pageQuery.getSize());
    }
}
