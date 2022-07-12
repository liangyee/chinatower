package com.vastio.bean.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Chen Xiaoyu
 * @Description:
 * @Date: Created in 10:14 2018/7/4
 */

@Data
public class ContractInfo extends RenewContract {
    @Excel(name = "起始日期")
    private Date start;
    @Excel(name = "终止日期")
    private Date end;
}
