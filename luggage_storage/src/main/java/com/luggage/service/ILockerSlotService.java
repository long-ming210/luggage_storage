package com.luggage.service;

import com.luggage.domain.po.LockerSlot;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luggage.domain.vo.SlotVO;

import java.util.List;

/**
 * <p>
 * 格口表 服务类
 * </p>
 *
 * @author 闽
 * @since 2026-06-06
 */
public interface ILockerSlotService extends IService<LockerSlot> {

    List<SlotVO> listSlotsByPointId(Long pointId);
}
