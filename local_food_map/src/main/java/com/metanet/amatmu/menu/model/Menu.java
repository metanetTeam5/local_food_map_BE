package com.metanet.amatmu.menu.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Menu {

    private Long id;
    private Long restaurantId; // Restaurant 엔터티 대신 ID를 사용
    private String menuName;
    private int price;
    private String menuContent;
    private String menuSellStatus;
    private String menuType;
    private int views;
    private List<Long> imageIds; // MenuImage 엔터티 대신 ID 리스트를 사용
    private String primaryimage;
}
