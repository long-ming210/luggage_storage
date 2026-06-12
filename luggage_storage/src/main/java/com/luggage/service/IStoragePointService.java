package com.luggage.service;

import com.luggage.domain.po.StoragePoint;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luggage.domain.vo.SlotVO;
import com.luggage.domain.vo.StoragePointListVO;

import java.util.List;

/**
 * <p>
 * 寄存点表 服务类
 * </p>
 *
 * @author 闽
 * @since 2026-06-06
 */
public interface IStoragePointService extends IService<StoragePoint> {

    List<StoragePointListVO> listActivePoints();


}
