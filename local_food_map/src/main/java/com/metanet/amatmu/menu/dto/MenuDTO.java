package com.metanet.amatmu.menu.dto;

import java.util.List;

import com.metanet.amatmu.menu.model.Menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuDTO {
    private Long id;
    private Long restaurantId;
    private String menuName;
    private int price;
    private String menuContent;
    private String menuSellStatus;
    private String menuType;
    private String primaryimage;
    private List<Long> imageIds;

    public Menu createMenu() {
        Menu menu = new Menu();
        menu.setId(this.id);
        menu.setRestaurantId(this.restaurantId);
        menu.setMenuName(this.menuName);
        menu.setPrice(this.price);
        menu.setMenuContent(this.menuContent);
        menu.setMenuSellStatus(this.menuSellStatus);
        menu.setMenuType(this.menuType);
        menu.setPrimaryimage(this.primaryimage);
        menu.setImageIds(this.imageIds);
        return menu;
    }

    public static MenuDTO of(Menu menu) {
        return MenuDTO.builder()
                .id(menu.getId())
                .restaurantId(menu.getRestaurantId())
                .menuName(menu.getMenuName())
                .price(menu.getPrice())
                .menuContent(menu.getMenuContent())
                .menuSellStatus(menu.getMenuSellStatus())
                .menuType(menu.getMenuType())
                .primaryimage(menu.getPrimaryimage())
                .imageIds(menu.getImageIds())
                .build();
    }
}
