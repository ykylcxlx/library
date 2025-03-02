package com.example.library.pojo.dto;

import com.example.library.common.pojo.dto.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class RecordDTO extends BaseDTO {
    @NotBlank(message = "用户名不能为空")
    String user_name;
    @NotBlank(message = "书名不能为空")
    String book_name;

    Date borrow_time;
    Date return_time;

}