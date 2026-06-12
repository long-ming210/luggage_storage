package com.luggage.controller;


import com.luggage.common.Result;
import com.luggage.domain.vo.SlotVO;
import com.luggage.service.impl.LockerSlotServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 格口表 前端控制器
 * </p>
 *
 * @author 闽
 * @since 2026-06-06
 */
@Slf4j
@RestController
@RequestMapping("/locker-slot")
@Api("格口管理")
@RequiredArgsConstructor
public class LockerSlotController {
    private final LockerSlotServiceImpl lockerSlotService;
    
    @GetMapping("/{pointId}")
    @ApiOperation("获取指定寄存点的格口信息")
    public Result<List<SlotVO>> listSlotsByPointId(@PathVariable Long pointId) {
        log.info("获取指定寄存点的格口信息: {}", pointId);
        return Result.success(lockerSlotService.listSlotsByPointId(pointId));
    }

}
