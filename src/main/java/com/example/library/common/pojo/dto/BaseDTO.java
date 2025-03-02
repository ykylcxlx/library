package com.example.library.common.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author WangYi
 */
@Data
public class BaseDTO {
	@Schema(name = "主键ID")
	Long id;
}
