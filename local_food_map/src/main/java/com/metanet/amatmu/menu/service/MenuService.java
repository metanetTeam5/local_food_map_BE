package com.metanet.amatmu.menu.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.metanet.amatmu.menu.dao.IMenuRepository;
import com.metanet.amatmu.menu.dto.MenuDto;
import com.metanet.amatmu.menu.model.Menu; 


@Service
public class MenuService implements IMenuService {
	
	@Autowired
	private IMenuRepository menuDao;
	
    @Override
    public List<MenuDto> getMenuList(Long restId) {
    	
    	List<MenuDto> result = new ArrayList<>();
        
    	List<Menu> menuList = menuDao.selectByRestId(restId);
    	
    	for (Menu menu : menuList) {
    		MenuDto dto = new MenuDto();
    		dto.setMenuId(menu.getMenuId());
    		dto.setMenuName(menu.getMenuName());
    		dto.setMenuPrice(menu.getMenuPrice());
    		dto.setMenuImg(menu.getMenuImg());
    		dto.setRestId(menu.getRestId());
    		
    		result.add(dto);
    	}
    	
        return result;
    }
}

