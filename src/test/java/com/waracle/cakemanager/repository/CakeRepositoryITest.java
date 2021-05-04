package com.waracle.cakemanager.repository;

import com.waracle.cakemanager.domain.entity.Cake;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.waracle.cakemanager.test.support.TestConstants.CAKE_1;
import static com.waracle.cakemanager.test.support.TestConstants.CAKE_2;
import static com.waracle.cakemanager.test.support.TestConstants.DESC_1;
import static com.waracle.cakemanager.test.support.TestConstants.DESC_2;
import static com.waracle.cakemanager.test.support.TestConstants.IMAGE_1;
import static com.waracle.cakemanager.test.support.TestConstants.IMAGE_2;
import static com.waracle.cakemanager.test.support.TestConstants.TITLE_1;
import static com.waracle.cakemanager.test.support.TestConstants.cloneCake;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext(classMode = AFTER_CLASS)
class CakeRepositoryITest {
    private static final String LONG_TEXT = "123456789012345678901234567890123456789012345678901234567890" +
            "12345678901234567890123456789012345678901234567890";
    private static final String VERY_LONG_TEXT = LONG_TEXT + LONG_TEXT + LONG_TEXT;
    private static final String NULL = null;

    @Autowired
    private CakeRepository classUnderTest;

    @Test
    public void givenTitleIsNull_whenSave_thenThrowException() {
        assertThrows(DataIntegrityViolationException.class, () ->
                classUnderTest.save(new Cake(NULL, DESC_1, IMAGE_1)));
    }

    @Test
    public void givenTitleLargerThan100_whenSave_thenThrowException() {
        assertThrows(DataIntegrityViolationException.class, () ->
                classUnderTest.save(new Cake(LONG_TEXT, DESC_1, IMAGE_1)));
    }

    @Test
    public void givenDescIsNull_whenSave_thenThrowException() {
        assertThrows(DataIntegrityViolationException.class, () ->
                classUnderTest.save(new Cake(TITLE_1, NULL, IMAGE_1)));
    }

    @Test
    public void givenDescLargerThan100_whenSave_thenThrowException() {
        assertThrows(DataIntegrityViolationException.class, () ->
                classUnderTest.save(new Cake(TITLE_1, LONG_TEXT, IMAGE_1)));
    }

    @Test
    public void givenImageIsNull_whenSave_thenThrowException() {
        assertThrows(DataIntegrityViolationException.class, () ->
                classUnderTest.save(new Cake(TITLE_1, DESC_1, NULL)));
    }

    @Test
    public void givenImageLargerThan300_whenSave_thenThrowException() {
        assertThrows(DataIntegrityViolationException.class, () ->
                classUnderTest.save(new Cake(TITLE_1, DESC_1, VERY_LONG_TEXT)));
    }

    @Test
    public void givenProperData_whenSave_thenSave() {
        Cake cake = classUnderTest.save(cloneCake(CAKE_1));

        assertNotNull(cake.getId());
        assertEquals(TITLE_1, cake.getTitle());
        assertEquals(DESC_1, cake.getDescription());
        assertEquals(IMAGE_1, cake.getImage());
    }

    @Test
    public void givenDuplicateTitle_whenSave_thenThrowException() {
        classUnderTest.save(cloneCake(CAKE_1));

        assertThrows(DataIntegrityViolationException.class, () ->
                classUnderTest.save(new Cake(TITLE_1, DESC_2, IMAGE_2)));
    }

    @Test
    public void whenFindAll_thenReturnAll() {
        Cake cake1 = classUnderTest.save(cloneCake(CAKE_1));
        Cake cake2 = classUnderTest.save(cloneCake(CAKE_2));

        assertEquals(asList(cake1, cake2), classUnderTest.findAll());
    }
}