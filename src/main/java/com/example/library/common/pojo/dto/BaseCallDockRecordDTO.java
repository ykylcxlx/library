package com.example.library.common.pojo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author WangYi
 */
@Data
public class BaseCallDockRecordDTO {
    @Schema(name = "applyData", description = "加密后参数")
    @NotBlank(message = "applyData不能为空")
    @JsonAlias("applyData")
    String dataContent;
}
