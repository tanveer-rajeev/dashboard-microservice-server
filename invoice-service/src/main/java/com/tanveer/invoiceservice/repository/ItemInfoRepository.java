package com.tanveer.invoiceservice.repository;

import com.tanveer.invoiceservice.entity.ItemInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemInfoRepository extends JpaRepository<ItemInfoEntity, Integer> {

    List<ItemInfoEntity> findAllByCategory(String category);
    ItemInfoEntity findByCode(String code);
    ItemInfoEntity findByName(String name);
}
