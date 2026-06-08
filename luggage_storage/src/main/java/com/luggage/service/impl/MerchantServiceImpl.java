package com.luggage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luggage.domain.po.Merchant;
import com.luggage.domain.vo.MerchantInfoVO;
import com.luggage.domain.vo.MerchantVO;
import com.luggage.mapper.MerchantMapper;
import com.luggage.service.IMerchantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luggage.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商户表 服务实现类
 * </p>
 *
 * @author 闽
 * @since 2026-06-06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MerchantServiceImpl extends ServiceImpl<MerchantMapper, Merchant> implements IMerchantService {
    private final MerchantMapper merchantMapper;

    
    @Override
    public MerchantVO login(String username, String password) {
        log.info("尝试登录，用户名: {}", username);

        Merchant merchant = merchantMapper.selectOne(new QueryWrapper<Merchant>().eq("username", username));
        
        if (merchant == null) {
            log.warn("登录失败，用户不存在: {}", username);
            throw new RuntimeException("用户不存在");
        }
        
        if (!merchant.getPasswordHash().equals(password)) {
            log.warn("登录失败，密码错误: {}", username);
            throw new RuntimeException("密码错误");
        }
        // 返回用户信息，包含用户详情和token
        MerchantInfoVO merchantInfoVO = new MerchantInfoVO();
        BeanUtils.copyProperties(merchant, merchantInfoVO);
        MerchantVO merchantVO = new MerchantVO();
        merchantVO.setUserInfo(merchantInfoVO);
        //生成token
        String token = JwtUtil
                .generateToken(merchant.getId(), "merchant", merchant.getUsername());
        merchantVO.setToken( token);

        return merchantVO;
    }
}
