package com.example.service;

import com.example.dto.Keyword;
import com.example.dto.Product;
import com.example.dto.ProductGroup;

import java.util.Set;

public interface LowestPriceService {
    Set getZsetValue(String key);
    int setNewProduct(Product product);
    int setNewProductGroup(ProductGroup productGroup);
    int setNewProductGroupToKeyword(String keyword, String productGroupId, double score);
    Keyword getLowestPriceProductByKeyword(String keyword);
}
