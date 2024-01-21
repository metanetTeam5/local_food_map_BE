package com.metanet.amatmu.menu.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.metanet.amatmu.menu.dto.MenuDto;
import com.metanet.amatmu.menu.dto.MenuRegisterDto;

public interface IMenuService {

    List<MenuDto> getMenuList(Long restId);
    String getMenuName(Long restId);
    MenuDto registerMenu(MenuRegisterDto menuRegisterDto, long restId);
    String uploadMenuImg(MultipartFile file);
}
