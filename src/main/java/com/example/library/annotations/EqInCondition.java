package com.example.library.annotations;

import java.lang.annotation.*;

/**
 * @Author WY
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface EqInCondition {
}
