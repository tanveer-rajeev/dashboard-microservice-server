package com.tanveer.invoiceservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "invoice")
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String date;
    private Integer orderNo;
    private String customerName;
    private Integer userId;
    private double totalGross;
    private double netPrice;
    private double discount;
    private double discountAmount;
    private double netTotal;
    private double grandTotal;
    private double depositedAmount;
    private double dueAmount;

    @ManyToMany
    @JoinTable(name = "invoice_items",
            joinColumns = @JoinColumn(name = "invoice_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"))
    private Set<ItemInfoEntity> items;

}
