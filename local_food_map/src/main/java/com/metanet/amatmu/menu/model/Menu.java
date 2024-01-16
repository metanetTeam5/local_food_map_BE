package com.metanet.amatmu.menu.model;

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

    private Long menuId;
    private String menuName;
    private String menuPrice;
    private String menuImg;
    private Long restId;
}
