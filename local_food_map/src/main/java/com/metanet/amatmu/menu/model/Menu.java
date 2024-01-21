package com.metanet.amatmu.menu.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Menu {

    private Long menuId;
    private String menuName;
    private int menuPrice;
    private String menuImg;
    private Long restId;
}
