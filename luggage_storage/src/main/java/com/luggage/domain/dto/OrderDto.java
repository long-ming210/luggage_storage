package com.luggage.domain.dto;

import lombok.Data;

import java.time.LocalDate;

import java.time.LocalDateTime;
@Data
public class OrderDto {
    // 格口ID
    Long slotId;
    // 预约时间
    LocalDateTime startTime;
    // 预约结束时间
    LocalDateTime endTime;
    
    String contactPhone;

}
