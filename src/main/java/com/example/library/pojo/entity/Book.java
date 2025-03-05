package com.example.library.pojo.entity;

import com.example.library.annotations.UniqCheck;
import com.example.library.annotations.UniqColumn;
import com.example.library.common.pojo.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author zyh
 * @create 2024/7/30
 */

@Data
@EqualsAndHashCode(callSuper = true)
@UniqCheck
public class Book extends BaseEntity {

    String bookName;
    @UniqColumn(message="已存在重复书籍")
    String isbn;
    String category;

    Boolean borrowed;
}
