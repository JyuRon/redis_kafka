package com.example.service.impl;

import com.example.dto.Product;
import com.example.service.LowestPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LowestPriceServiceImpl implements LowestPriceService {

    private final RedisTemplate<String, Object> redisTemplate;
    @Override
    public Set getZsetValue(String key) {
        Set myTempSet = new HashSet();
        myTempSet = redisTemplate.opsForZSet().rangeWithScores(key, 0,9);
        return myTempSet;
    }

    @Override
    public int setNewProduct(Product product) {
        int rank = 0;
        redisTemplate.opsForZSet().add(product.getProductGroupId(), product.getProductId(), product.getPrice());
        rank = redisTemplate.opsForZSet().rank(product.getProductGroupId(), product.getProductId()).intValue();

        return rank;
    }
}
