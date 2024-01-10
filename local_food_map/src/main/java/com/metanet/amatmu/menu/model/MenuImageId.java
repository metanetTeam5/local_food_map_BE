package com.metanet.amatmu.menu.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuImageId implements Serializable {


    private Menu menu;

    private Long id;    //MenuImage의 id 매핑
}