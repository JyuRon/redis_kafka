package com.example.service;

import com.example.dto.Product;

import java.util.Set;

public interface LowestPriceService {
    Set getZsetValue(String key);
    int setNewProduct(Product product);
}
