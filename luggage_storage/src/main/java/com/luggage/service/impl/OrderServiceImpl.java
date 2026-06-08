package com.luggage.service.impl;

import com.luggage.domain.po.Order;
import com.luggage.mapper.OrderMapper;
import com.luggage.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author 闽
 * @since 2026-06-06
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
