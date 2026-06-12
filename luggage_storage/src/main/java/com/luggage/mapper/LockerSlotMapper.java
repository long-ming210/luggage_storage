package com.luggage.mapper;

import com.luggage.domain.po.LockerSlot;
import com.luggage.domain.vo.SlotVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 格口表 Mapper 接口
 * </p>
 *
 * @author 闽
 * @since 2026-06-06
 */
public interface LockerSlotMapper extends BaseMapper<LockerSlot> {

    List<SlotVO> selectSlotsWithStatus(@Param("pointId") Long pointId);

}
