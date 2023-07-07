package com.tanveer.invoiceservice.dto;

import com.tanveer.invoiceservice.entity.ItemInfoEntity;
import com.tanveer.invoiceservice.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    private UserEntity user;
    private ItemInfoEntity itemInfo;

}
