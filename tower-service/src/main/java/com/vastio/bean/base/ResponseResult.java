package com.vastio.bean.base;

import lombok.Data;

import java.util.List;

@Data
public class ResponseResult<T> {
    private int total;
    private String message;
    private List<T> results;
    private int code;
}
