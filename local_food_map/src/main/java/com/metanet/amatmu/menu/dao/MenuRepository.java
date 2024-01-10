package com.metanet.amatmu.menu.dao;

import java.util.List;
import java.util.Optional;

import com.metanet.amatmu.menu.model.Menu;

public interface MenuRepository {

    List<Menu> findByRestaurant(Long restaurantId);
    List<Menu> findByMenuType(String menuType);
    Optional<Menu> findById(Long id);
    Menu save(Menu menu);
    void delete(Menu menu);
}
