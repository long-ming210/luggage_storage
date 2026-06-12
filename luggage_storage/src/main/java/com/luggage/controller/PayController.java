package com.luggage.controller;

import com.luggage.common.Result;
import com.luggage.service.IOrderService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/pay")
@RestController
@Slf4j
@RequiredArgsConstructor
@Api(tags = "支付相关接口")
public class PayController {
    private final IOrderService orderService;
    @PostMapping("/order/{id}")
    public Result payOrder(@PathVariable Long id) {
        log.info("支付订单: {}", id);
        orderService.payOrder(id);
        return Result.success("支付成功");

    }
}
