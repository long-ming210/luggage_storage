package com.luggage.mapper;

import com.luggage.domain.po.StoragePoint;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luggage.domain.vo.SlotVO;
import com.luggage.domain.vo.StoragePointListVO;

import java.util.List;

/**
 * <p>
 * 寄存点表 Mapper 接口
 * </p>
 *
 * @author 闽
 * @since 2026-06-06
 */
public interface StoragePointMapper extends BaseMapper<StoragePoint> {

    List<StoragePointListVO> selectPointWithMinpriceWithSlot();


}
