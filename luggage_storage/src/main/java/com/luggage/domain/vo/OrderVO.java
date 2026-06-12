package com.luggage.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author 闽
 * @since 2026-06-06
 */
@Data
public class OrderVO  {

    @ApiModelProperty(value = "订单ID")
    private Long id;

    @ApiModelProperty(value = "订单号，全局唯一")
    private String orderNo;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "格口ID")
    private Long slotId;

    @ApiModelProperty(value = "状态：booked已预约 stored已存入 picked_up已取件 cancelled已取消")
    private String status;

    @ApiModelProperty(value = "预约时间")
    private LocalDateTime bookTime;

    @ApiModelProperty(value = "总金额（元）")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "支付状态：0未付 1已付")
    private Integer payStatus;

    @ApiModelProperty(value = "存取二维码内容")
    private String qrCode;

    @ApiModelProperty(value = "用户电话")
    private String contactPhone;

    @ApiModelProperty(value = "取件时间")
    private LocalDateTime pickUpTime;

    @ApiModelProperty(value = "存件时间")
    private LocalDateTime storeTime;
    @ApiModelProperty(value = "格口编号，如 A-01")
    private String slotNumber;
    @ApiModelProperty("寄存点名字")
    private String pointName;
}
