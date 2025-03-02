package com.example.library.aspects;

import com.example.library.common.controller.BaseExtController;
import com.example.library.common.pojo.dto.BaseDTO;
import com.example.library.etc.ServiceException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author WY
 **/
@Aspect
@Component
@Order(Integer.MAX_VALUE - 1)
public class ControllerCheckAspect {
    @Pointcut("execution(* com.example.library..controller.*.save(com.example.library.common.pojo.dto.BaseDTO+))")
    public void saveHandle() {
    }

    @Pointcut("execution(* com.example.library..controller..controller.*.update(com.example.library.common.pojo.dto.BaseDTO+))")
    public void updateHandle() {
    }

    @Before(value = "saveHandle()")
    public void checkSave(JoinPoint joinPoint) {
        if (getId(joinPoint) != null) {
            throw new ServiceException("主键ID不为空");
        }
    }

    @Before(value = "updateHandle()")
    public void checkUpdate(JoinPoint joinPoint) {
        if (getId(joinPoint) == null) {
            throw new ServiceException("主键ID不允许为空");
        }
    }

    public Long getId(JoinPoint joinPoint) {
        //获取切面对象
        Object target = joinPoint.getTarget();
        if (!(target instanceof BaseExtController)) {
            //若非BaseControllerExt类型，跳过处理
            throw new ServiceException("aspect target type error");
        }

        //save、update方法参数对象校验
        Object dtoEntity = joinPoint.getArgs()[0];
        if (! (dtoEntity instanceof BaseDTO)) {
            throw new ServiceException("method param type error");
        }

        return ((BaseDTO) dtoEntity).getId();
    }
}
