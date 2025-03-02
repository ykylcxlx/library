package com.example.library.common.pojo.entity;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * @author WangYi
 */
@Data
public class BaseEntity {
    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "主键id")
    @TableId(type = IdType.ASSIGN_ID)
    Long id;

    @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    @TableField(fill = FieldFill.INSERT)
    Date createTime;

    @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)//作用：指定在插入或更新数据时如何填充该字段。
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    Date updateTime;
}
