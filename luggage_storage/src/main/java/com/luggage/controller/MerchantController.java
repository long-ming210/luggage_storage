package com.luggage.controller;


import com.luggage.common.Result;
import com.luggage.domain.dto.LoginDTO;
import com.luggage.domain.vo.MerchantVO;
import com.luggage.service.IMerchantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商户表 前端控制器
 * </p>
 *
 * @author 闽
 * @since 2026-06-06
 */
@RestController
@RequestMapping("/merchant")
@Api(value = "merchant", tags = "商户管理")
@RequiredArgsConstructor
public class MerchantController {
    private final IMerchantService merchantService;
    

    
    @PostMapping("/login")
    @ApiOperation("管理员登录")
    public Result<MerchantVO> login(@RequestBody LoginDTO loginDTO) {
        MerchantVO merchantVO = merchantService.login(loginDTO.getUsername(), loginDTO.getPassword());
        return Result.success(merchantVO);
    }
}
