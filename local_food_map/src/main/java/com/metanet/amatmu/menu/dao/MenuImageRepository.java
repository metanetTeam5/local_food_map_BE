package com.metanet.amatmu.menu.dao;

import java.util.List;
import java.util.Optional;

import com.metanet.amatmu.menu.model.Menu;
import com.metanet.amatmu.menu.model.MenuImage;
import com.metanet.amatmu.menu.model.MenuImageId;

public interface MenuImageRepository {

    Optional<MenuImage> findById(Long menuImgId);

    List<MenuImage> findByMenu(Menu menu);

    List<MenuImage> findByMenuId(Long menuId);

	void delete(MenuImage image);

}
