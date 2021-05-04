package com.waracle.cakemanager.client;

import com.waracle.cakemanager.config.FeignConfig;
import com.waracle.cakemanager.domain.dto.CakeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name= "cake-data-client", url = "${cake.load.server}", configuration = FeignConfig.class)
public interface CakeDataClient {

    @GetMapping(value = "${cake.load.context-path}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    List<CakeDto> getCakesData();
}
