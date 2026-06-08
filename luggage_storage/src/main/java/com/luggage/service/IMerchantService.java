package com.luggage.service;

import com.luggage.domain.po.Merchant;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luggage.domain.vo.MerchantVO;

/**
 * <p>
 * 商户表 服务类
 * </p>
 *
 * @author 闽
 * @since 2026-06-06
 */
public interface IMerchantService extends IService<Merchant> {

    MerchantVO login(String username, String passwordHash);
}
