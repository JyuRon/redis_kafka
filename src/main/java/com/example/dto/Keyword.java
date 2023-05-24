package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Keyword {
    private String keyword;

    /**
     * 동일 상품 판매처 그룹에 대한 리스트
     * 즉 키워드에 대한 여러가지 상품이 나오게 된다.
     */
    private List<ProductGroup> productGroupList;
}
