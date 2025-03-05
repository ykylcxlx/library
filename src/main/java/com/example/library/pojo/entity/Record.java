package com.example.library.pojo.entity;

import com.example.library.annotations.UniqCheck;
import com.example.library.annotations.UniqColumn;
import com.example.library.common.pojo.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author zyh
 * @version 3.0
 */

@Data
@EqualsAndHashCode(callSuper = true)
@UniqCheck
public class Record extends BaseEntity {

    @UniqColumn(message = "已存在同名书籍")
    String bookName;

    String user_name;
    Date borrow_time;
    Date return_time;


}
