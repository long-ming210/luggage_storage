package com.luggage.service.impl;

import com.luggage.domain.po.StoragePoint;
import com.luggage.domain.vo.SlotVO;
import com.luggage.domain.vo.StoragePointListVO;
import com.luggage.mapper.StoragePointMapper;
import com.luggage.service.IStoragePointService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 寄存点表 服务实现类
 * </p>
 *
 * @author 闽
 * @since 2026-06-06
 */
@Service
@RequiredArgsConstructor
public class StoragePointServiceImpl extends ServiceImpl<StoragePointMapper, StoragePoint> implements IStoragePointService {
    private final StoragePointMapper storagePointMapper;
    private final LockerSlotServiceImpl lockerSlotService;
    @Override
    public List<StoragePointListVO> listActivePoints() {

        List<StoragePointListVO> storagePointListVOs = storagePointMapper.selectPointWithMinpriceWithSlot();
        return storagePointListVOs;
    }


}
