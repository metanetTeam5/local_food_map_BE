//package com.metanet.amatmu.menu.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.metanet.amatmu.menu.dto.MenuDTO;
//import com.metanet.amatmu.menu.service.MenuService;
//import com.metanet.amatmu.member.model.Member;
//
//@RestController
//@RequestMapping("/menu")
//public class MenuController {
//
//    @Autowired
//    private MenuService menuService;
//
//    // 해당 가게의 모든 메뉴 정보 리스트
//    @GetMapping("/info/{restId}")
//    public ResponseEntity<List<MenuDTO>> getMenuInfoList(@PathVariable Long vendorId) {
//        List<MenuDTO> menuList = menuService.getMenuList(vendorId);
//        return ResponseEntity.ok(menuList);
//    }
//
//    // 메뉴 등록
//    @PostMapping("/info/insertMenu")
//    public ResponseEntity<Void> insertMenu(@RequestBody MenuDTO menuDTO, 
//                                           @RequestParam(required = false, value = "file") MultipartFile[] uploadFiles) {
////    	Member member = 
////        menuService.insertMenu(member, menuDTO, uploadFiles);
//        return ResponseEntity.ok().build();
//    }
//
//    // 메뉴 수정
//    @PutMapping("/info/changeMenu")
//    public ResponseEntity<Void> updateMenu(@RequestBody MenuDTO menuDTO, 
//                                           @RequestParam(required = false, value = "file") MultipartFile[] uploadFiles) {
////    	Member member = 
////        menuService.updateMenu(member, menuDTO, uploadFiles);
//        return ResponseEntity.ok().build();
//    }
//
//    // 메뉴 삭제
//    @DeleteMapping("/info/deleteMenu/{menuId}")
//    public ResponseEntity<Void> deleteMenu(@PathVariable Long menuId) {
////    	Member member = 
////        menuService.deleteMenu(member, memberId);
//        return ResponseEntity.ok().build();
//    }
//}
//
//
//
//
