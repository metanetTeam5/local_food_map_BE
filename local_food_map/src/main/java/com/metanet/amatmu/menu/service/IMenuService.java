package com.metanet.amatmu.menu.service;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.metanet.amatmu.menu.dto.MenuDTO;
import com.metanet.amatmu.menu.model.Menu;

public interface IMenuService {

    List<MenuDTO> getMenuList(Long vendorId);

    void insertMenu(Menu menu, MenuDTO menuDTO, MultipartFile[] uploadFiles) throws IOException;

    void updateMenu(Menu menu, MenuDTO menuDTO, MultipartFile[] uploadFiles) throws IOException;

    void deleteMenu(Long menuId);

    List<String> getRecommendedMenuTypes();
}
