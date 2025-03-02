package com.example.library.annotations;

import java.lang.annotation.*;

/**
 * @Author WangYi
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface UniqCheck {
    boolean checkAll() default true;
}
