package com.waracle.cakemanager.service;

import com.waracle.cakemanager.client.CakeDataClient;
import com.waracle.cakemanager.repository.CakeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import static com.waracle.cakemanager.test.support.TestConstants.CAKE_1;
import static com.waracle.cakemanager.test.support.TestConstants.CAKE_2;
import static com.waracle.cakemanager.test.support.TestConstants.CAKE_DTO_1;
import static com.waracle.cakemanager.test.support.TestConstants.CAKE_DTO_LIST;
import static com.waracle.cakemanager.test.support.TestConstants.CAKE_LIST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class CakeServiceUTest {
    @Mock
    private CakeDataClient cakeDataClient;

    @Mock
    private CakeRepository cakeRepository;

    @InjectMocks
    private CakeService classUnderTest;

    @Test
    public void givenNoData_whenLoadCakeData_thenDoNothing() {
        doReturn(null).when(cakeDataClient).getCakesData();

        classUnderTest.loadCakeData();

        verify(cakeDataClient).getCakesData();
        verifyNoInteractions(cakeRepository);
    }

    @Test
    public void givenCakeData_whenLoadCakeData_thenAddAllCakes() {
        doReturn(CAKE_DTO_LIST).when(cakeDataClient).getCakesData();
        doReturn(CAKE_1).when(cakeRepository).save(CAKE_1);
        doReturn(CAKE_2).when(cakeRepository).save(CAKE_2);

        classUnderTest.loadCakeData();

        verify(cakeDataClient).getCakesData();
        verify(cakeRepository).save(CAKE_1);
        verify(cakeRepository).save(CAKE_2);
    }

    @Test
    public void givenDataIsNull_whenLoadCakeData_thenThrowNullException() {
        assertThrows(NullPointerException.class, () -> classUnderTest.addCake(null));
    }

    @Test
    public void givenPersistenceException_whenLoadCakeData_thenNull() {
        doThrow(DataIntegrityViolationException.class).when(cakeRepository).save(CAKE_1);

        assertNull(classUnderTest.addCake(CAKE_DTO_1));

        verify(cakeRepository).save(CAKE_1);
    }

    @Test
    public void givenNoException_whenLoadCakeData_thenSaveCake() {
        doReturn(CAKE_1).when(cakeRepository).save(CAKE_1);

        assertEquals(CAKE_DTO_1, classUnderTest.addCake(CAKE_DTO_1));

        verify(cakeRepository).save(CAKE_1);
    }

    @Test
    public void whenGetCakeList_thenReturnCakeList() {
        doReturn(CAKE_LIST).when(cakeRepository).findAll();

        assertEquals(CAKE_DTO_LIST, classUnderTest.getCakeList());

        verify(cakeRepository).findAll();
    }
}