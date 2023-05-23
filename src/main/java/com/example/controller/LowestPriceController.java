package com.example.controller;

import com.example.dto.Product;
import com.example.service.LowestPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class LowestPriceController {

    private final LowestPriceService lowestPriceService;

    @GetMapping("/getZsetValue")
    public Set getZsetValue(String key){
        return lowestPriceService.getZsetValue(key);
    }

    @PostMapping("/product")
    public int setNewProduct(@RequestBody Product product){
        return lowestPriceService.setNewProduct(product);
    }
}
