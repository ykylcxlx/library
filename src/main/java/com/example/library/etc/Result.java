package com.example.library.etc;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Result<T> {
    Integer status;
    String msg;
    T data;
}

