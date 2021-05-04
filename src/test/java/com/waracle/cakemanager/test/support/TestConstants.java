package com.waracle.cakemanager.test.support;

import com.waracle.cakemanager.domain.dto.CakeDto;
import com.waracle.cakemanager.domain.entity.Cake;

import java.util.List;

import static java.util.Arrays.asList;

public class TestConstants {
    public static final String TITLE_1 = "title1";
    public static final String TITLE_2 = "title2";
    public static final String TITLE_3 = "title3";

    public static final String DESC_1 = "desc1";
    public static final String DESC_2 = "desc1";
    public static final String DESC_3 = "desc3";

    public static final String IMAGE_1 = "image1";
    public static final String IMAGE_2 = "image2";
    public static final String IMAGE_3 = "image3";

    public static final CakeDto CAKE_DTO_1 = new CakeDto(TITLE_1, DESC_1, IMAGE_1);
    public static final CakeDto CAKE_DTO_2 = new CakeDto(TITLE_2, DESC_2, IMAGE_2);
    public static final CakeDto CAKE_DTO_3 = new CakeDto(TITLE_3, DESC_3, IMAGE_3);

    public static final Cake CAKE_1 = new Cake(TITLE_1, DESC_1, IMAGE_1);
    public static final Cake CAKE_2 = new Cake(TITLE_2, DESC_2, IMAGE_2);

    public static final List<CakeDto> CAKE_DTO_LIST = asList(CAKE_DTO_1, CAKE_DTO_2);
    public static final List<Cake> CAKE_LIST = asList(CAKE_1, CAKE_2);

    public static Cake cloneCake(Cake cake) {
        return new Cake(cake.getTitle(), cake.getDescription(), cake.getImage());
    }

}
