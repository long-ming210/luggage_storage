package com.luggage.service.impl;

import com.luggage.domain.po.StoragePoint;
import com.luggage.mapper.StoragePointMapper;
import com.luggage.service.IStoragePointService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 寄存点表 服务实现类
 * </p>
 *
 * @author 闽
 * @since 2026-06-06
 */
@Service
public class StoragePointServiceImpl extends ServiceImpl<StoragePointMapper, StoragePoint> implements IStoragePointService {

}
