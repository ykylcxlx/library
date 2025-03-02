package com.example.library.common.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author WangYi
 */
@Data
public class BaseVO {
    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "主键id")
    Long id;
}
