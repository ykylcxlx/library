package com.example.library.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.library.common.pojo.entity.BaseEntity;
import com.example.library.common.pojo.model.PageQuery;
import com.example.library.common.service.BaseService;
import com.example.library.util.PageQueryUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author WangYi
 */
public class BaseController<T extends BaseEntity, S extends BaseService<T>> {
	@Autowired
	protected S service;

	@PostMapping("/remove")
	@Operation(summary = "删除", description = "传入ID主键")
	public Boolean remove(@Parameter(name = "主键", required = true) @RequestParam Long id) {
		return service.removeById(id);
	}

	@GetMapping("/detail")
	@Operation(summary = "查看详情", description = "传入主键ID")
	public T detail(@Parameter(name = "主键", required = true) @RequestParam Long id) {
		return service.getById(id);
	}

	@PostMapping("/page")
	@Parameters({
			@Parameter(name = "current", description = "当前页", in = ParameterIn.QUERY, schema = @Schema(type = "int"), required = true),
			@Parameter(name = "size", description = "每页的数量", in = ParameterIn.QUERY, schema = @Schema(type = "int"), required = true)
	})
	@Operation(summary = "分页条件查询", description = "传入查询对象")
	public IPage<T> page(@RequestBody(required = false) T entity, @Parameter(hidden = true) PageQuery query) {
		return service.page(PageQueryUtil.getPage(query), entity, query.getQueryType());
	}



}
