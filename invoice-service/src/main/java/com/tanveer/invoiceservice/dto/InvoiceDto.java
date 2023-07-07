package com.tanveer.invoiceservice.dto;

import com.tanveer.invoiceservice.entity.ItemInfoEntity;
import com.tanveer.invoiceservice.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InvoiceDto {
    private String date;
    private Integer orderNo;
    private String customerName;
    private Integer userId;
    private Set<ItemInfoEntity> items;
    private double totalGross;
    private double netPrice;
    private double discount;
    private double discountAmount;
    private double netTotal;
    private double grandTotal;
    private double prvDue;
    private double depositedAmount;
    private double dueAmount;
}
