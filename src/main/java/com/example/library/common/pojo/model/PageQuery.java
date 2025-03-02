package com.example.library.common.pojo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * @author WangYi
 */
@Data
public class PageQuery {
    @Schema(description = "当前页")
    Integer current;

    @Schema(description = "每页最大查询数量")
    Integer size;

    @Schema(hidden = true, description = "条件匹配类型")
    @Min(value = 0, message = "条件匹配类型有误")
    @Max(value = 2, message = "条件匹配类型有误")
    Integer queryType;
}
