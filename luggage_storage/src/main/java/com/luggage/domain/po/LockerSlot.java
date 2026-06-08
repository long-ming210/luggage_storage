package com.luggage.domain.po;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 格口表
 * </p>
 *
 * @author 闽
 * @since 2026-06-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("locker_slot")
@ApiModel(value="LockerSlot对象", description="格口表")
public class LockerSlot implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "格口ID")
    @TableId(value = "id", type = IdType.AUTO)
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

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updatedAt;


}
