package com.luggage.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luggage.common.Result;
import com.luggage.domain.dto.OrderDto;
import com.luggage.domain.po.Order;
import com.luggage.domain.vo.OrderVO;
import com.luggage.service.IOrderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final IOrderService orderService;
    
    @ApiOperation("添加订单")
    @PostMapping
    public Result<OrderVO> addOrder(@RequestBody OrderDto orderDto) {
        log.info("添加订单: {}", orderDto);
        return Result.success(orderService.addOrder(orderDto));
    }
    @ApiOperation("获取订单列表")
    @GetMapping
    public Result<List<OrderVO>> listOrders() {
        log.info("获取订单列表");
        return orderService.listOrederVO();
    }
    @ApiOperation("根据id查询单个订单")
    @GetMapping("/{id}")
    public Result<OrderVO> getOrderById(@PathVariable Long id) {
        log.info("根据id查询单个订单: {}", id);
        OrderVO orderVO = orderService.getOrderVOById(id);
        return Result.success(orderVO);
    }
    @ApiOperation("存件")
    @PostMapping("/{id}/store")
    public Result<OrderVO> storeOrder(@PathVariable Long id) {
        log.info("存件: {}", id);
        return Result.success(orderService.storeOrder(id));
    }
    @ApiOperation("取件")
    @PostMapping("/{id}/pickup")
    public Result<OrderVO> pickupOrder(@PathVariable Long id) {
        log.info("取件: {}", id);
        return Result.success(orderService.pickupOrder(id));
    }
    @ApiOperation("查看进行中的订单")
    @GetMapping("/current")
    public Result<List<OrderVO>> listCurrentOrders() {
        log.info("查看进行中的订单");
        return orderService.listCurrentOrders();
    }
}
