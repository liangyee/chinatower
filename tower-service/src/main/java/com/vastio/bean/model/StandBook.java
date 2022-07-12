package com.vastio.bean.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Chen Xiaoyu
 * @Description:
 * @Date: Created in 17:05 2018/7/3
 */

@Data
public class StandBook extends ContractInfo {
    @Excel(name = "合同属性")
    private String attribute;
    @Excel(name = "联系电话")
    private String phoneNumber;
    @Excel(name = "所属业主名称（甲方）")
    private String ownerName;
    @Excel(name = "付款时间")
    private Date payTime;
    @Excel(name = "房租")
    private Double rent;
    @Excel(name = "税额")
    private Double tax;

}
