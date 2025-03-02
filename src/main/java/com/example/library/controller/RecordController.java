package com.example.library.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.library.common.controller.BaseController;
import com.example.library.common.controller.BaseExtController;
import com.example.library.common.pojo.model.PageQuery;
import com.example.library.constant.AuthorizeConstant;
import com.example.library.mapper.RecordMapper;
import com.example.library.pojo.dto.RecordDTO;
import com.example.library.pojo.entity.Record;
import com.example.library.service.RecordService;
import com.example.library.service.impl.RecordServiceImpl;
import com.example.library.util.PageQueryUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/record")
@Tag(name = "书籍信息接口", description = "书籍信息接口")
@PreAuthorize(AuthorizeConstant.HAS_ROLE_ADMIN)
public class RecordController extends BaseExtController <Record, RecordDTO, RecordService> {
    @Resource
    private RecordServiceImpl recordService;

    @Override
    @PostMapping("/save")
    @Operation(summary = "新增", description = "传入新增对象")
    public Boolean save(@Valid @RequestBody RecordDTO dto) {

        Record record = recordPropertySet(dto);
        return service.save(record);
    }

    @Override
    @PostMapping("/update")
    @Operation(summary = "续借", description = "传入修改对象")
    public Boolean update(@Valid @RequestBody RecordDTO dto) {
        Record record = recordPropertySet(dto);
        return service.updateById(record);
    }

    @Override
    @GetMapping("/detail")
    @Operation(summary = "查看详情", description = "传入主键ID")
    public Record detail(@Parameter(name = "主键", required = true) @RequestParam Long id) {
        return service.getById(id);
    }


    @GetMapping("/userRecord")
    @Operation(summary = "查看详情", description = "传入人名")
    public Record detail(@Parameter(name = "name", required = true) @RequestParam String name) {
        return recordService.getRecordByUserName(name);

    }

    @Override
    @PostMapping("/page")
    @Parameters({
            @Parameter(name = "current", description = "当前页", in = ParameterIn.QUERY, schema = @Schema(type = "int"), required = true),
            @Parameter(name = "size", description = "每页的数量", in = ParameterIn.QUERY, schema = @Schema(type = "int"), required = true)
    })
    @Operation(summary = "分页条件查询", description = "传入查询对象")
    public IPage<Record> page(@RequestBody(required = false) Record entity, @Parameter(hidden = true) PageQuery query) {
        return service.page(PageQueryUtil.getPage(query), entity, query.getQueryType());
    }

    @Override
    @PostMapping("/remove")
    @Operation(summary = "删除", description = "传入ID主键")
    public Boolean remove(@Parameter(name = "主键", required = true) @RequestParam Long id) {
        return service.removeById(id);
    }

//    @GetMapping("/borrow")
//    @Operation(summary = "查询借阅信息", description = "传入用户名")
//    public Record getBorrow(@Parameter(name = "书名", required = true) @RequestParam String name) {
//        return recordMapper.selectRecordByName(name);
//    }

    private Record recordPropertySet(RecordDTO dto) {
        Record record = new Record();
        BeanUtil.copyProperties(dto, record);
        return record;
    }
}