package com.metanet.amatmu.menu.service;

import java.util.List;

import com.metanet.amatmu.menu.dto.MenuDto;

public interface IMenuService {

    List<MenuDto> getMenuList(Long restId);
    String getMenuName(Long restId);
}
