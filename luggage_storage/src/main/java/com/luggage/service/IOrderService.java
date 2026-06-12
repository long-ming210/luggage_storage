package com.luggage.service;

import com.luggage.common.Result;
import com.luggage.domain.dto.OrderDto;
import com.luggage.domain.po.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luggage.domain.vo.OrderVO;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author 闽
 * @since 2026-06-06
 */
public interface IOrderService extends IService<Order> {

    OrderVO addOrder(OrderDto orderDto);
    void payOrder(Long id);
    OrderVO storeOrder(Long id);
    OrderVO pickupOrder(Long id);

    Result<List<OrderVO>> listOrederVO();

    OrderVO getOrderVOById(Long id);

    Result<List<OrderVO>> listCurrentOrders();
}
