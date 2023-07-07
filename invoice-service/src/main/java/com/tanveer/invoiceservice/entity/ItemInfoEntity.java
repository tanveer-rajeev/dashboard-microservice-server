package com.tanveer.invoiceservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "item_info")
public class ItemInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String category;
    private String code;
    private String name;
    private Integer quantity;
    private double unitPrice;
    private double grossPrice;
    private double discount;
    private double netPrice;
    private double vat;
    private double total;

}
