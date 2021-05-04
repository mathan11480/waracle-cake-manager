package com.waracle.cakemanager.controller;

import com.waracle.cakemanager.domain.dto.CakeDto;
import com.waracle.cakemanager.service.CakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CakeController {
    private final CakeService cakeService;

    @GetMapping("/cakes")
    public List<CakeDto> getCakeList() {
        return cakeService.getCakeList();
    }

    @PostMapping("/cake")
    public CakeDto addCake(@RequestBody CakeDto cakeDto) {
        return cakeService.addCake(cakeDto);
    }
}
