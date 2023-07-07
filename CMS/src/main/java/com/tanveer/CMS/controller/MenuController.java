package com.tanveer.CMS.controller;

import com.tanveer.CMS.dto.MenuDTO;
import com.tanveer.CMS.entity.MenuEntity;
import com.tanveer.CMS.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @PostMapping("/save/{pId}")
    public MenuEntity saveMenu(@RequestBody MenuDTO menuDTO, @PathVariable Integer pId){
        return menuService.addMenu(menuDTO,pId);
    }

    @GetMapping("/{id}")
    public MenuEntity getMenuById(@PathVariable Integer id){
        return menuService.getMenuById(id);
    }

    @GetMapping("/by/pId/{pId}")
    public List<MenuEntity> getMenuByParentId(@PathVariable Integer pId){
        return menuService.getMenuByParentId(pId);
    }

    @GetMapping("/all")
    public List<MenuEntity> getAllMenu(){
        return menuService.getAllMenu();
    }
}
