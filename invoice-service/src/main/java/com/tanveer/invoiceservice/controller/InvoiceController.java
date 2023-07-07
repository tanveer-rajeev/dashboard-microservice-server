package com.tanveer.invoiceservice.controller;

import com.tanveer.invoiceservice.dto.InvoiceDto;
import com.tanveer.invoiceservice.dto.TransactionResponse;
import com.tanveer.invoiceservice.entity.InvoiceEntity;
import com.tanveer.invoiceservice.entity.ItemInfoEntity;
import com.tanveer.invoiceservice.service.InvoiceService;
import com.tanveer.invoiceservice.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private ItemService itemService;


    @PostMapping("/save/item")
    public TransactionResponse saveItem(@RequestBody ItemInfoEntity itemInfo){
        return itemService.saveItem(itemInfo);
    }

    @GetMapping("/item/by/category/{category}")
    public List<ItemInfoEntity> getItemByCategory(@PathVariable String category){
        return itemService.findItemByCategory(category);
    }

    @GetMapping("/by/date/{from}/{to}")
    public List<InvoiceEntity> getInvoicesBetweenGivenDateRange(@PathVariable String from,@PathVariable String to){
        return invoiceService.findInvoicesByGivenDateRange(from,to);
    }

    @GetMapping("/by/orderNo/{orderNo}")
    public InvoiceEntity getInvoiceByOrderNo(@PathVariable Integer orderNo){
        return invoiceService.findInvoiceByOrderNo(orderNo);
    }

    @GetMapping("/by/customerName/{name}")
    public List<InvoiceEntity> getInvoiceByCustomerName(@PathVariable String name){
        return invoiceService.findInvoiceByCustomerName(name);
    }

    @GetMapping("/items")
    public List<ItemInfoEntity> getItemByCode(){
        return itemService.findAllItem();
    }

    @DeleteMapping("/item/{id}")
    public TransactionResponse deleteItem(@PathVariable Integer id){
        return itemService.deleteItem(id);
    }

    @GetMapping("/item/by/name/{name}")
    public ItemInfoEntity getItemByName(@PathVariable String name){
        return itemService.findItemByName(name);
    }

    @PostMapping
    public TransactionResponse saveInvoice(@RequestBody InvoiceDto invoice){
        return invoiceService.saveInvoice(invoice);
    }

    @GetMapping("/privateInventory")
    public String getPrivateInventory(){
        return "Private Inventory";
    }

    @GetMapping("/{id}")
    public InvoiceDto getBillById(@PathVariable Integer id){
        return invoiceService.getBillById(id);
    }

}
