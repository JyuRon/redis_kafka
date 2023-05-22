package com.example.controller;

import com.example.service.LowestPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
