package com.waracle.cakemanager.service;

import com.waracle.cakemanager.client.CakeDataClient;
import com.waracle.cakemanager.domain.dto.CakeDto;
import com.waracle.cakemanager.domain.entity.Cake;
import com.waracle.cakemanager.repository.CakeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class CakeService {
    private final CakeDataClient cakeDataClient;
    private final CakeRepository cakeRepository;

    @PostConstruct
    public void loadCakeData() {
        ofNullable(cakeDataClient.getCakesData())
                .orElse(emptyList())
                .forEach(this::addCake);
    }

    public CakeDto addCake(CakeDto cakeDto) {
        requireNonNull(cakeDto, "CakeDto cannot be null");

        try {
            Cake cake = cakeRepository.save(new Cake(cakeDto.getTitle(), cakeDto.getDesc(), cakeDto.getImage()));

            return new CakeDto(cake.getTitle(), cake.getDescription(), cake.getImage());
        } catch (DataIntegrityViolationException dive) {
            log.warn("Error persisting entry {} with exception {}", cakeDto, dive.getMessage());
            return null;
        }
    }

    public List<CakeDto> getCakeList() {
        return cakeRepository.findAll().stream()
                .map(cake -> new CakeDto(cake.getTitle(), cake.getDescription(), cake.getImage())).collect(toList());
    }

}
