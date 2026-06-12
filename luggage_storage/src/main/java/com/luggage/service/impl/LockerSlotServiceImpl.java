package com.luggage.service.impl;

import com.luggage.domain.po.LockerSlot;
import com.luggage.domain.vo.SlotVO;
import com.luggage.mapper.LockerSlotMapper;
import com.luggage.service.ILockerSlotService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 格口表 服务实现类
 * </p>
 *
 * @author 闽
 * @since 2026-06-06
 */
@Service
@RequiredArgsConstructor
public class LockerSlotServiceImpl extends ServiceImpl<LockerSlotMapper, LockerSlot> implements ILockerSlotService {
    private final LockerSlotMapper lockerSlotMapper;

    /**
     * 根据寄存点ID查询格口（状态通过 LEFT JOIN order 表实时推导）
     * @param pointId
     * @return
     */
    public List<SlotVO> listSlotsByPointId(Long pointId) {
        List<SlotVO> slots = lockerSlotMapper.selectSlotsWithStatus(pointId);
        if (slots == null || slots.isEmpty()) {
            throw new RuntimeException("No slots found for the given point ID");
        }
        return slots;
    }
}
