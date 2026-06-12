package com.luggage.controller;


import com.luggage.common.Result;
import com.luggage.domain.po.StoragePoint;
import com.luggage.domain.vo.SlotVO;
import com.luggage.domain.vo.StoragePointListVO;
import com.luggage.service.IStoragePointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 寄存点表 前端控制器
 * </p>
 *
 * @author 闽
 * @since 2026-06-06
 */
@RestController
@RequestMapping("/storage-point")
@Api("寄存点")
@RequiredArgsConstructor
public class StoragePointController {
    private final IStoragePointService storagePointService;
    @ApiOperation("获取所有正常状态的寄存点")
    @GetMapping
    public Result<List<StoragePointListVO>> listActivePoints() {
        return Result.success(storagePointService.listActivePoints());
    }



}
