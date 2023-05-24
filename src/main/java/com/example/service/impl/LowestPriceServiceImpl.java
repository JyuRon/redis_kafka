package com.example.service.impl;

import com.example.dto.Keyword;
import com.example.dto.Product;
import com.example.dto.ProductGroup;
import com.example.service.LowestPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LowestPriceServiceImpl implements LowestPriceService {

    private final RedisTemplate<String, Object> redisTemplate;
    @Override
    public Set getZsetValue(String key) {
        Set myTempSet = redisTemplate.opsForZSet().rangeWithScores(key, 0,9);
        return myTempSet;
    }

    @Override
    public int setNewProduct(Product product) {
        int rank = 0;
        redisTemplate.opsForZSet().add(product.getProductGroupId(), product.getProductId(), product.getPrice());
        rank = redisTemplate.opsForZSet().rank(product.getProductGroupId(), product.getProductId()).intValue();

        return rank;
    }

    @Override
    public int setNewProductGroup(ProductGroup productGroup) {
        List<Product> productList = productGroup.getProductList();
        productList.forEach(product ->
                redisTemplate.opsForZSet().add(
                        productGroup.getProductGroupId(),
                        product.getProductId(),
                        product.getPrice()
                ));

        return redisTemplate.opsForZSet().zCard(productGroup.getProductGroupId()).intValue();
    }

    @Override
    public int setNewProductGroupToKeyword(String keyword, String productGroupId, double score){
        redisTemplate.opsForZSet().add(keyword, productGroupId, score);
        int rank = redisTemplate.opsForZSet().rank(keyword, productGroupId).intValue();
        return rank;
    }

    @Override
    public Keyword getLowestPriceProductByKeyword(String keyword) {
        return new Keyword(keyword, getProductGroupByKeyword(keyword));
    }

    public List<ProductGroup> getProductGroupByKeyword(String keyword){

        // 키워드에 해당하는 제품(Product Group Id) 들을 가져옴
        Set<Object> productGroupByKeyword = redisTemplate.opsForZSet().reverseRange(keyword, 0, 9);
        if(ObjectUtils.isEmpty(productGroupByKeyword)){
            return null;
        }

        // 각 제품에 해당하는 최저가 판매처 10개 정보 가져오기
        List<ProductGroup> result = new ArrayList<>();
        productGroupByKeyword.forEach(
                // keyword 로 조회한 상품 정보(product group id)로 loop
                productGroupId -> {
                    String groupId = (String) productGroupId;
                    List<Product> productList = new ArrayList<>();
                    redisTemplate.opsForZSet()
                            .rangeWithScores(groupId, 0, 9)
                            // 상품별 판매처 정보를 Product 객체로 매핑
                            .forEach(
                                    product -> {
                                        Product resultProduct = new Product(
                                                groupId,
                                                (String) product.getValue(),
                                                product.getScore().intValue()
                                        );
                                        productList.add(resultProduct);
                                    }
                            );
                    result.add(new ProductGroup(groupId, productList));
                }
        );

        return result;
    }
}
