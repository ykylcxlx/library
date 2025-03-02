package com.example.library.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.library.common.controller.BaseController;
import com.example.library.common.controller.BaseExtController;
import com.example.library.common.pojo.model.PageQuery;
import com.example.library.constant.AuthorizeConstant;
import com.example.library.mapper.BookMapper;
import com.example.library.pojo.dto.BookDTO;
import com.example.library.pojo.entity.Book;
import com.example.library.service.BookService;
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
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/book")
@Tag(name = "书籍信息接口", description = "书籍信息接口")
@PreAuthorize(AuthorizeConstant.HAS_ROLE_ADMIN)
public class BookController extends BaseExtController <Book, BookDTO, BookService> {
    @Resource
    private BookService bookservice;
    @Resource
    //@Lazy
    BookMapper bookmapper;
    @Override
    @PostMapping("/save")
    @Operation(summary = "新增", description = "传入新增对象")
    public Boolean save(@Valid @RequestBody BookDTO dto) {

        Book book = bookPropertySet(dto);
        
        return service.save(book);
    }

    @Override
    @PostMapping("/update")
    @Operation(summary = "修改", description = "传入修改对象")
    public Boolean update(@Valid @RequestBody BookDTO dto) {
        Book book = bookPropertySet(dto);
        return service.updateById(book);
    }

    @Override
    @GetMapping("/detail")
    @Operation(summary = "查看详情", description = "传入主键ID")
    public Book detail(@Parameter(name = "主键", required = true) @RequestParam Long id) {
        return service.getById(id);
    }

    @Override
    @PostMapping("/page")
    @Parameters({
            @Parameter(name = "current", description = "当前页", in = ParameterIn.QUERY, schema = @Schema(type = "int"), required = true),
            @Parameter(name = "size", description = "每页的数量", in = ParameterIn.QUERY, schema = @Schema(type = "int"), required = true)
    })
    @Operation(summary = "分页条件查询", description = "传入查询对象")
    public IPage<Book> page(@RequestBody(required = false) Book entity, @Parameter(hidden = true) PageQuery query) {
        return service.page(PageQueryUtil.getPage(query), entity, query.getQueryType());
    }

    @Override
    @PostMapping("/remove")
    @Operation(summary = "删除", description = "传入ID主键")
    public Boolean remove(@Parameter(name = "主键", required = true) @RequestParam Long id) {

        return service.removeById(id);
    }


    @GetMapping("/borrow")
    @Operation(summary = "查询借阅信息",description = "传入书名")
    public Book getBorrow(@Parameter(name="书名",required=true) @RequestParam String name ){
        return bookmapper.selectBookByName(name);
    }

    private Book bookPropertySet(BookDTO dto) {
        Book book = new Book();
        BeanUtil.copyProperties(dto, book);
        return book;
    }
}
