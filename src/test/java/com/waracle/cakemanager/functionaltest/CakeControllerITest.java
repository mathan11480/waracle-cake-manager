package com.waracle.cakemanager.functionaltest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cakemanager.domain.dto.CakeDto;
import com.waracle.cakemanager.test.support.CakeWireMockInitializer;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.waracle.cakemanager.test.support.TestConstants.CAKE_DTO_1;
import static com.waracle.cakemanager.test.support.TestConstants.CAKE_DTO_2;
import static com.waracle.cakemanager.test.support.TestConstants.CAKE_DTO_3;
import static com.waracle.cakemanager.test.support.TestConstants.CAKE_DTO_LIST;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {CakeWireMockInitializer.class})
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class CakeControllerITest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    public void getCakes() throws Exception {
        assertEquals(CAKE_DTO_LIST, requestGetCakes());
    }

    @Test
    @Order(2)
    public void addCake() throws Exception {
        MvcResult results = mockMvc.perform(post("/cake")
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(CAKE_DTO_3))
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(CAKE_DTO_3, extractCakeDto(results));

        assertEquals(asList(CAKE_DTO_1, CAKE_DTO_2, CAKE_DTO_3), requestGetCakes());
    }

    private List<CakeDto> requestGetCakes() throws Exception {
        MvcResult results = mockMvc.perform(get("/cakes")
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        return extractCakeDtoList(results);
    }

    private List<CakeDto> extractCakeDtoList(MvcResult results) throws Exception{
        return objectMapper.readValue(results.getResponse().getContentAsString(), new TypeReference<>() {});
    }

    private CakeDto extractCakeDto(MvcResult results) throws Exception{
        return objectMapper.readValue(results.getResponse().getContentAsString(), CakeDto.class);
    }
}
