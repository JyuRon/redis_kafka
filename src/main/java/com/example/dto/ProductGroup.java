package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
/** 동일 상품에 대한 판매처 정보 */
public class ProductGroup {

    private String productGroupId;
    private List<Product> productList;
}
