package com.vastio.bean.base;

import lombok.Data;

/**
 * define 接口请求失败后的统一返回数据结构
 *
 * @author xlch
 * @Date 2018-03-02 10:21
 */

@Data
public class ApiError {
    private int code;
    private String message;
}
