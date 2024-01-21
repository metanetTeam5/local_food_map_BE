package com.metanet.amatmu.menu.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.metanet.amatmu.menu.dao.IMenuRepository;
import com.metanet.amatmu.menu.dto.MenuDto;
import com.metanet.amatmu.menu.dto.MenuRegisterDto;
import com.metanet.amatmu.menu.model.Menu;
import com.metanet.amatmu.utils.S3Uploader; 


@Service
public class MenuService implements IMenuService {
	
	@Autowired
	private IMenuRepository menuDao;
	
	@Autowired
	private S3Uploader s3Uploader;
	
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

	@Override
	public String getMenuName(Long restId) {
		List<Menu> menuList = menuDao.selectByRestId(restId);
		
		String result = "";
		
		for (Menu menu : menuList) {
			result += menu.getMenuName() + " ";
		}
		
		return result;
	}

	@Override
	public MenuDto registerMenu(MenuRegisterDto menuRegisterDto, long restId) {
		Menu menu = new Menu();
		
		menu.setMenuId(menuDao.selectMaxMenuNo() + 1);
		menu.setMenuName(menuRegisterDto.getMenuName());
		menu.setMenuPrice(menuRegisterDto.getMenuPrice());
		menu.setMenuImg(menuRegisterDto.getMenuImg());
		menu.setRestId(restId);
		
		menuDao.insertMenu(menu);
		
		MenuDto dto = new MenuDto();
		dto.setMenuId(menu.getMenuId());
		dto.setMenuName(menu.getMenuName());
		dto.setMenuPrice(menu.getMenuPrice());
		dto.setMenuImg(menu.getMenuImg());
		dto.setRestId(menu.getRestId());
	
		return dto;
	}

	@Override
	public String uploadMenuImg(MultipartFile file) {
		String imgUrl = s3Uploader.fileUpload(file);
		
		return imgUrl;
	}
}

