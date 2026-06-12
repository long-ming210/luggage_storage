package com.luggage.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商户表
 * </p>
 *
 * @author 闽
 * @since 2026-06-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("merchant")
@ApiModel(value="Merchant对象", description="商户表")
public class Merchant extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "登录名")
    private String username;

    @ApiModelProperty(value = "BCrypt加密密码")
    private String passwordHash;

    @ApiModelProperty(value = "商户名称")
    private String name;

    @ApiModelProperty(value = "联系电话")
    private String phone;

}
