package com.luggage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.zxing.WriterException;
import com.luggage.common.Result;
import com.luggage.domain.dto.OrderDto;
import com.luggage.domain.po.LockerSlot;
import com.luggage.domain.po.Order;
import com.luggage.domain.vo.OrderVO;
import com.luggage.mapper.OrderMapper;
import com.luggage.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luggage.util.QcCodeUtil;
import com.luggage.util.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author 闽
 * @since 2026-06-06
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    private final OrderMapper orderMapper;
    private final LockerSlotServiceImpl lockerSlotService;
    private final StoragePointServiceImpl storagePointService;

    /**
     * 添加订单
     * @param orderDto
     * @return
     */

    public OrderVO addOrder(OrderDto orderDto) {
        Order order = new Order();
        BeanUtil.copyProperties(orderDto, order);
        String orderNo = "DD"+ IdUtil.getSnowflakeNextIdStr();
        order.setOrderNo(orderNo);
        order.setUserId(UserContext.getUserId());
        order.setStatus("booked");
        order.setBookTime(orderDto.getStartTime());
        order.setStoreTime(orderDto.getEndTime());

        //格口单价
        LockerSlot slot = lockerSlotService.getById(orderDto.getSlotId());
        BigDecimal pricePerHour = slot.getPricePerHour();

        //计算时长（小时）
        long minutes = java.time.Duration.between(orderDto.getStartTime(), orderDto.getEndTime()).toMinutes();
        int hours = (int) Math.ceil(minutes / 60.0);

        order.setTotalAmount(pricePerHour.multiply(BigDecimal.valueOf(hours)));
        try {
            order.setQrCode(QcCodeUtil.generateBase64(orderNo));
        } catch (Exception e) {
            throw new RuntimeException("二维码生成失败", e);
        }
        lockerSlotService.updateById( slot.setStatus("occupied"));
        save(order);
        OrderVO orderVO = new OrderVO();
        BeanUtil.copyProperties(order, orderVO)
        ;
        orderVO.setSlotNumber(slot.getSlotNumber());
        orderVO.setPointName(storagePointService.getById(slot.getPointId()).getName());
        return  orderVO;
    }

    @Override
    //先查询是否有无订单，状态是否预约
    public void payOrder(Long id) {
        Order order = getById(id);
        if (!order.getStatus().equals("booked")) {
             throw new RuntimeException("订单状态错误");
        }
        order.setPayStatus(1);
        order.setStoreTime(LocalDateTime.now());
        updateById(order);
    }

    @Override
    public OrderVO storeOrder(Long id) {
        Order order = getById(id);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        if (!order.getStatus().equals("booked")) {
            throw new RuntimeException("订单状态错误，仅已预约订单可存件");
        }
        if (order.getPayStatus() != 1) {
            throw new RuntimeException("订单未支付，请先完成支付");
        }
        order.setStatus("stored");
        order.setStoreTime(LocalDateTime.now());
        updateById(order);

        OrderVO orderVO = new OrderVO();
        BeanUtil.copyProperties(order, orderVO);
        orderVO.setSlotNumber(lockerSlotService.getById(orderVO.getSlotId()).getSlotNumber());
        orderVO.setPointName(storagePointService.getById(lockerSlotService.getById(orderVO.getSlotId()).getPointId()).getName());
        return orderVO;
    }

    @Override
    public OrderVO pickupOrder(Long id) {
        Order order = getById(id);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        if (!order.getStatus().equals("stored")) {
            throw new RuntimeException("订单状态错误，仅已存入订单可取件");
        }
        order.setStatus("picked_up");
        order.setPickUpTime(LocalDateTime.now());
        updateById(order);

        LockerSlot slot = lockerSlotService.getById(order.getSlotId());
        lockerSlotService.updateById(slot.setStatus("idle"));

        OrderVO orderVO = new OrderVO();
        BeanUtil.copyProperties(order, orderVO);
        orderVO.setSlotNumber(slot.getSlotNumber());
        orderVO.setPointName(storagePointService.getById(slot.getPointId()).getName());
        return orderVO;
    }

    @Override
    public Result<List<OrderVO>> listOrederVO() {
        List<Order> list = list();
        List<OrderVO> orderVOList = getOrderVOList(list);
        return Result.success(orderVOList);
    }

    @NotNull
    private List<OrderVO> getOrderVOList(List<Order> list) {
        List<OrderVO> orderVOList = list.stream().map(order -> {
            OrderVO orderVO = new OrderVO();
            BeanUtil.copyProperties(order, orderVO);
            orderVO.setSlotNumber(lockerSlotService.getById(orderVO.getSlotId()).getSlotNumber());
            orderVO.setPointName(storagePointService.getById(lockerSlotService.getById(orderVO.getSlotId()).getPointId()).getName());
            return orderVO;
        }).toList();
        return orderVOList;
    }

    @Override
    public OrderVO getOrderVOById(Long id) {
        OrderVO orderVO = new OrderVO();
        BeanUtil.copyProperties(getById(id), orderVO);
        orderVO.setSlotNumber(lockerSlotService.getById(orderVO.getSlotId()).getSlotNumber());
        orderVO.setPointName(storagePointService.getById(lockerSlotService.getById(orderVO.getSlotId()).getPointId()).getName());
        return orderVO;
    }

    @Override
    public Result<List<OrderVO>> listCurrentOrders() {
        LambdaQueryWrapper<Order> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Order::getUserId, UserContext.getUserId());
        wrapper.and(wrapper1 -> wrapper1.eq(Order::getStatus, "booked").eq(Order::getPayStatus, 1))
                .or(wrapper1 -> wrapper1.eq(Order::getStatus, "stored").eq(Order::getPayStatus, 1));
        List<Order> list = list(wrapper);
        List<OrderVO> orderVOList = getOrderVOList(list);

        return Result.success(orderVOList);

    }
}
