package com.waracle.cakemanager.test.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.waracle.cakemanager.test.support.TestConstants.CAKE_DTO_1;
import static com.waracle.cakemanager.test.support.TestConstants.CAKE_DTO_2;
import static java.util.Arrays.asList;
import static java.util.Map.of;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

public class CakeWireMockInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        WireMockServer wireMockServer = new WireMockServer(new WireMockConfiguration().dynamicPort());
        wireMockServer.start();

        configurableApplicationContext
                .getBeanFactory()
                .registerSingleton("wireMockServer", wireMockServer);

        configurableApplicationContext.addApplicationListener(applicationEvent -> {
            if (applicationEvent instanceof ContextClosedEvent) {
                wireMockServer.stop();
            }
        });

        TestPropertyValues
                .of(of("cake.load.server", "http://localhost:" + wireMockServer.port()))
                .applyTo(configurableApplicationContext);


        stubCakeLoadResponse(wireMockServer);
    }

    private void stubCakeLoadResponse(WireMockServer wireMockServer) {
        wireMockServer.stubFor(get("/cake.json")
                .willReturn(aResponse()
                        .withHeader("Content-Type", TEXT_PLAIN_VALUE)
                        .withBody(getCakeListJson())
                ));
    }

    private String getCakeListJson() {
        try {
            return new ObjectMapper().writeValueAsString(asList(CAKE_DTO_1, CAKE_DTO_2, CAKE_DTO_1, CAKE_DTO_2));
        } catch (JsonProcessingException jpe) {
            throw new RuntimeException(jpe);
        }
    }
}
