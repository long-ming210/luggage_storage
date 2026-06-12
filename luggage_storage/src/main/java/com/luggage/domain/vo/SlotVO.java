package com.luggage.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 格口表
 * </p>
 *
 * @author 闽
 * @since 2026-06-06
 */
@Data
public class SlotVO implements Serializable {

    @ApiModelProperty(value = "格口ID")
    private Long id;

    @ApiModelProperty(value = "所属寄存点ID")
    private Long pointId;

    @ApiModelProperty(value = "格口编号，如 A-01")
    private String slotNumber;

    @ApiModelProperty(value = "尺寸：S小 M中 L大")
    private String sizeType;

    @ApiModelProperty(value = "每小时价格（元）")
    private BigDecimal pricePerHour;

    @ApiModelProperty(value = "状态：idle空闲 occupied占用 fault故障")
    private String status;

    @ApiModelProperty(value = "物理柜蓝牙设备ID（第二阶段用）")
    private String lockerDeviceId;


}
