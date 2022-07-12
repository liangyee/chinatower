package com.vastio.bean.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: Chen Xiaoyu
 * @Description:
 * @Date: Created in 16:36 2018/7/4
 */

@Data
public class RenewContract {
    private int id;

    @Excel(name = "所属区域")
    private String region;
    @Excel(name = "站点编码")
    private String code;
    @Excel(name = "站点名称")
    private String siteName;
    @Excel(name = "合同名称")
    private String contractName;
    @Excel(name = "合同起始日期")
    private Date contractStart;
    @Excel(name = "合同终止日期")
    private Date contractEnd;

}
