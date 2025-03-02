package com.example.library.common.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReflectUtil;
import com.example.library.common.pojo.dto.BaseDTO;
import com.example.library.common.pojo.entity.BaseEntity;
import com.example.library.common.service.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.ParameterizedType;

/**
 * @Author WangYi
 */
public class BaseExtController<T extends BaseEntity, E extends BaseDTO, S extends BaseService<T>> extends BaseController<T, S> {
	@PostMapping("/save")
	@Operation(summary = "新增", description = "传入新增对象")
	public Boolean save(@Valid @RequestBody E dto) {
		T entity = ReflectUtil.newInstance(((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName());
		BeanUtil.copyProperties(dto, entity);
		return service.save(entity);
	}

	@PostMapping("/update")
	@Operation(summary = "修改", description = "传入修改对象")
	public Boolean update(@Valid @RequestBody E dto) {
		T entity = ReflectUtil.newInstance(((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName());
		BeanUtil.copyProperties(dto, entity);
		return service.updateById(entity);
	}



}
