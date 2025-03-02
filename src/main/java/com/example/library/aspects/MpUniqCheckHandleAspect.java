package com.example.library.aspects;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.TypeUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.example.library.annotations.DisableCheck;
import com.example.library.annotations.UniqCheck;
import com.example.library.annotations.UniqColumn;
import com.example.library.common.controller.BaseExtController;
import com.example.library.common.pojo.dto.BaseDTO;
import com.example.library.common.pojo.entity.BaseEntity;
import com.example.library.common.service.BaseService;
import com.example.library.etc.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WY
 **/
@Slf4j
@Aspect
@Component
@Order
@SuppressWarnings("unchecked")
public class MpUniqCheckHandleAspect {
    /**
     * 数据库对象唯一性检查路径方法规则
     */
    @Pointcut("execution(* com.example.library..controller.*.save(com.example.library.common.pojo.dto.BaseDTO+)) || " +
            "execution(* com.example.library..controller.*.update(com.example.library.common.pojo.dto.BaseDTO+))")
    public void uniqHandle() {
    }

    @Before(value = "uniqHandle()")
    public void checkUniq(JoinPoint joinPoint) {
        //获取切面对象
        Object target = joinPoint.getTarget();
        if (!(target instanceof BaseExtController)) {
            //若非BaseExtController类型，跳过处理
            return;
        }

        //切面controller实际类型
        Class<?> controllerClass = target.getClass();

        //获取切点方法
        Method targetMethod = ClassUtil.getDeclaredMethod(controllerClass, joinPoint.getSignature().getName());
        if (AnnotationUtil.hasAnnotation(controllerClass, DisableCheck.class) || AnnotationUtil.hasAnnotation(targetMethod, DisableCheck.class)) {
            //若切面controller或切点方法有DisableCheck注解，跳过处理
            return;
        }
        //数据库对象泛型Type
        Type dbEntityType = ((ParameterizedType) controllerClass.getGenericSuperclass()).getActualTypeArguments()[0];

        //获取数据库对象类型
        Class<?> dbEntityClass = TypeUtil.getClass(dbEntityType);

        //数据库对象类型必须有启用唯一性校验注解，否则跳过处理
        UniqCheck uniqCheckAnnotation = AnnotationUtil.getAnnotation(dbEntityClass, UniqCheck.class);
        if (uniqCheckAnnotation == null) {
            return;
        }

        //实例化数据库对象
        Object dbEntity = ReflectUtil.newInstance(dbEntityClass);
        //save、update方法参数对象
        Object dtoEntity = joinPoint.getArgs()[0];
        //将save update方法的参数DTO对象复制给数据库对象
        BeanUtil.copyProperties(dtoEntity, dbEntity);

        //数据库对象属性集
        Field[] fields = ReflectUtil.getFields(dbEntityClass);
        //将带有唯一检查注解的对象属性与注解所注明的唯一性冲突msg组织形成哈希表
        Map<String, String> checkMap = new HashMap<>(16);
        for (Field field : fields) {

            UniqColumn columnAnnotation = field.getAnnotation(UniqColumn.class);
            if (columnAnnotation == null) {
                //若属性上无@UniqColumn注解，继续下一个属性比对
                ReflectUtil.setFieldValue(dbEntity, field, null);
                continue;
            }
            Object fieldVal = ReflectUtil.getFieldValue(dbEntity, field);
            if (ObjectUtils.isEmpty(fieldVal)) {
                throw new ServiceException("数据错误");
            }

            checkMap.put(field.getName(), columnAnnotation.message());

        }
        if (checkMap.size() == 0) {
            throw new ServiceException("数据错误");
        }

        //service泛型Type
        Type serviceType = ((ParameterizedType) controllerClass.getGenericSuperclass()).getActualTypeArguments()[2];
        System.out.println("serviceType:" + serviceType);
        //service对象
        Object service = SpringUtil.getBean(TypeUtil.getClass(serviceType));

        //反射调用service对象的getOne方法，以查询要新增/更新的数据对象是否存在
        BaseEntity searchResult = (BaseEntity) ((BaseService) service).getOne(dbEntity, true);
        if (searchResult == null) {
            //不存在则进行后续正常新增/更新调用
            return;
        }
        //获取请求参数的数据主键ID
        Long dtoEntityId = ((BaseDTO) dtoEntity).getId();
        if (dtoEntityId != null) {
            if (dtoEntityId.equals(searchResult.getId())) {
                //方法参数对象ID值不为空，则方法为update更新方法，判断ID是否一致，一致则说明更新的是已存在的对象记录，且唯一性检查的字段未发生改变
                return;
            }
        }

        for (String checkProperty : checkMap.keySet()) {
            if (! ReflectUtil.getFieldValue(searchResult, checkProperty).equals(ReflectUtil.getFieldValue(dbEntity, checkProperty))) {
                //若唯一性检查字段查询结果对象与DTO对象不一致，在对象为检查所有字段情况下则抛出异常，若为检查任意字段，则继续下一字段比较
                if (uniqCheckAnnotation.checkAll()) {
                    throw new ServiceException("数据错误");
                }
                continue;
            }
            //抛出唯一性检查异常
            throw new ServiceException(checkMap.get(checkProperty));
        }
    }
}
