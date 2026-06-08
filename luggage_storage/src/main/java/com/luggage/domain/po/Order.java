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
 * 订单表
 * </p>
 *
 * @author 闽
 * @since 2026-06-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("order")
@ApiModel(value="Order对象", description="订单表")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单ID")
    @TableId(value = "id", type = IdType.AUTO)
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

    @ApiModelProperty(value = "实际存入时间")
    private LocalDateTime storeTime;

    @ApiModelProperty(value = "实际取出时间")
    private LocalDateTime pickUpTime;

    @ApiModelProperty(value = "总金额（元）")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "支付状态：0未付 1已付")
    private Integer payStatus;

    @ApiModelProperty(value = "存取二维码内容")
    private String qrCode;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updatedAt;


}
