package com.waracle.cakemanager.controller;

import com.waracle.cakemanager.service.CakeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.waracle.cakemanager.test.support.TestConstants.CAKE_DTO_1;
import static com.waracle.cakemanager.test.support.TestConstants.CAKE_DTO_LIST;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CakeControllerUTest {
    @Mock
    private CakeService cakeService;

    @InjectMocks
    private CakeController classUnderTest;

    @Test
    public void whenGetCakeList_thenReturnList() {
        doReturn(CAKE_DTO_LIST).when(cakeService).getCakeList();

        assertSame(CAKE_DTO_LIST, classUnderTest.getCakeList());

        verify(cakeService).getCakeList();
    }

    @Test
    public void whenAddCake_thenSaveCake() {
        doReturn(CAKE_DTO_1).when(cakeService).addCake(CAKE_DTO_1);

        assertSame(CAKE_DTO_1, classUnderTest.addCake(CAKE_DTO_1));

        verify(cakeService).addCake(CAKE_DTO_1);
    }
}