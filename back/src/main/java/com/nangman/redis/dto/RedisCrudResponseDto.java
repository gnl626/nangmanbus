package com.nangman.redis.dto;

import com.nangman.redis.domain.RedisCrud;
import lombok.Getter;

@Getter
public class RedisCrudResponseDto {
    private String key;
    private String subKey;
    private String value;

    public RedisCrudResponseDto(RedisCrud redisHash) {
        this.key = redisHash.getKey();
        this.subKey = redisHash.getSubKey();
        this.value = redisHash.getValue();

    }
}
