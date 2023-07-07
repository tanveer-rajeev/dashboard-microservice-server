package com.tanveer.CMS.service;

import com.tanveer.CMS.dto.MenuDTO;
import com.tanveer.CMS.entity.MenuEntity;
import com.tanveer.CMS.repository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private ModelMapper modelMapper;

    public MenuEntity addMenu(MenuDTO menuDTO, Integer pId) {

        MenuEntity menuEntity = modelMapper.map(menuDTO,MenuEntity.class);
        int totalMenuInDB = menuRepository.findAll().size();
        menuEntity.setAscOrder(totalMenuInDB + 1);
        menuEntity.setParentId(pId);
        return menuRepository.save(menuEntity);
    }

    public MenuEntity getMenuById(Integer id){
        return menuRepository.findById(id).stream().findFirst().get();
    }


    public List<MenuEntity> getMenuByParentId(Integer id){
        return menuRepository.findAllMenuByParentId(id);
    }

    public List<MenuEntity> getAllMenu(){
        return menuRepository.findAll();
    }
}
