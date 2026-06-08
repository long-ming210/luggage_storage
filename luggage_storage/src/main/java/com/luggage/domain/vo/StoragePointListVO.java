package com.luggage.domain.vo;

import lombok.Data;

@Data
public class StoragePointListVO {
    // 寄存点信息
    private String name;
    // 详细地址
    private String address;
    // 营业时间
    private String businessHours;
    // 空闲寄存格
    private Integer idleSlots;
    // 最低价格
    private Integer minPrice;

}
