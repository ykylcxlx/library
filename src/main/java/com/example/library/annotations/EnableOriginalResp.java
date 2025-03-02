package com.example.library.annotations;

import java.lang.annotation.*;

/**
 * @author WY
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface EnableOriginalResp {
}
