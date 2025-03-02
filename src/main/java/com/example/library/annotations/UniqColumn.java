package com.example.library.annotations;

import java.lang.annotation.*;

/**
 * 唯一约束字段属性
 * @Author WangYi
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface UniqColumn {
    String message() default "已存在重复数据";
}
