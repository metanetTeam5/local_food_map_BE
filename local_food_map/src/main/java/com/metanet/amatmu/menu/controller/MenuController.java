package com.metanet.amatmu.menu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.metanet.amatmu.menu.dto.MenuDto;
import com.metanet.amatmu.menu.service.IMenuService;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @GetMapping("/list/{restId}")
    public ResponseEntity<List<MenuDto>> getMenuInfoList(@PathVariable Long restId) {
        List<MenuDto> menuList = menuService.getMenuList(restId);
        return ResponseEntity.ok(menuList);
    }
    
    @GetMapping("/namelist/{restId}")
    public ResponseEntity<String> getMenuName(@PathVariable Long restId) {
    	return ResponseEntity.ok(menuService.getMenuName(restId));
    }
}




