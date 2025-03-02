package com.example.library.annotations;

import java.lang.annotation.*;

/**
 * 此注解仅针对save、update方法，带有此注解即标明当前方法不再通过切面逻辑进行数据库对象的唯一性校验
 * @Author WangYi
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DisableCheck {
}
