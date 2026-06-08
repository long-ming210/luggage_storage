package com.luggage.domain.vo;

import lombok.Data;

@Data
public class UserVO {
    private String token;

    private UserInfoVO userInfo;
}
