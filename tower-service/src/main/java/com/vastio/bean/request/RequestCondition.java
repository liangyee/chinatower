package com.vastio.bean.request;

import lombok.Data;

/**
 * @Author: Chen Xiaoyu
 * @Description:
 * @Date: Created in 17:38 2018/7/3
 */

@Data
public class RequestCondition {
    private String region;
    private String code;
    private String siteName;
    private String startTime;
    private String endTime;
    private int curPage;
    private int pageSize;
}
