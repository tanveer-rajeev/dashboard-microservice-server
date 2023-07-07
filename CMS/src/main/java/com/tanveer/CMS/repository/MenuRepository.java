package com.tanveer.CMS.repository;

import com.tanveer.CMS.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Integer> {
    @Query(value = "SELECT * from menu m where m.parent_id = :pId ",nativeQuery = true)
    List<MenuEntity> findAllMenuByParentId(@Param("pId") Integer pId);
}
