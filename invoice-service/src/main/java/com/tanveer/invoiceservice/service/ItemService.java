package com.tanveer.invoiceservice.service;

import com.tanveer.invoiceservice.dto.TransactionResponse;
import com.tanveer.invoiceservice.entity.ItemInfoEntity;
import com.tanveer.invoiceservice.repository.ItemInfoRepository;
import com.tanveer.invoiceservice.util.CalculateBill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemInfoRepository itemInfoRepository;

    public TransactionResponse saveItem(ItemInfoEntity itemInfoEntity) {

        double baseAmount = itemInfoEntity.getUnitPrice() * itemInfoEntity.getQuantity();
        itemInfoEntity.setGrossPrice(baseAmount);

        double vat = CalculateBill.percentage(baseAmount, itemInfoEntity.getVat());
        baseAmount += vat;
        if (itemInfoEntity.getDiscount() > 0)
            baseAmount -= CalculateBill.percentage(baseAmount, itemInfoEntity.getDiscount());

        itemInfoEntity.setNetPrice(baseAmount);
        itemInfoEntity.setTotal(baseAmount);
        itemInfoRepository.save(itemInfoEntity);

        return new TransactionResponse("Item successfully added");
    }

    public TransactionResponse deleteItem(Integer id){
        Optional<ItemInfoEntity> first = itemInfoRepository.findById(id).stream().findFirst();

        first.ifPresent(itemInfo -> itemInfoRepository.delete(itemInfo));
        return new TransactionResponse("Item deleted successfully");
    }

    public List<ItemInfoEntity> findItemByCategory(String category){
        return itemInfoRepository.findAllByCategory(category);
    }

    public List<ItemInfoEntity> findAllItem(){
        return itemInfoRepository.findAll();
    }

    public ItemInfoEntity findItemByName(String name){
        return itemInfoRepository.findByName(name);
    }
}
