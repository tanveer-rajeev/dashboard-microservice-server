package com.tanveer.invoiceservice.service;

import com.tanveer.invoiceservice.dto.InvoiceDto;
import com.tanveer.invoiceservice.dto.TransactionRequest;
import com.tanveer.invoiceservice.dto.TransactionResponse;
import com.tanveer.invoiceservice.entity.InvoiceEntity;
import com.tanveer.invoiceservice.entity.ItemInfoEntity;
import com.tanveer.invoiceservice.entity.UserEntity;
import com.tanveer.invoiceservice.repository.InvoiceRepository;
import com.tanveer.invoiceservice.repository.ItemInfoRepository;
import com.tanveer.invoiceservice.util.CalculateBill;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class InvoiceService {

    private final Logger logger = LoggerFactory.getLogger(InvoiceService.class);

    @Autowired
    private ItemInfoRepository itemInfoRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public TransactionResponse saveInvoice(InvoiceDto invoiceDto) {

        String username = invoiceDto.getCustomerName();

        try {
            InvoiceEntity invoice = modelMapper.map(invoiceDto, InvoiceEntity.class);
            UserEntity user = restTemplate.getForObject("http://localhost:9090/api/v1/user/getUser/{username}", UserEntity.class, username);
            Set<ItemInfoEntity> invoiceItems = new HashSet<>();

            invoice.setUserId(user.getId());
            invoice.setCustomerName(invoiceDto.getCustomerName());
            invoice.setOrderNo(invoiceDto.getOrderNo());
            invoice.setTotalGross(invoiceDto.getTotalGross());
            invoice.setNetPrice(invoiceDto.getNetPrice());
            invoice.setNetTotal(invoiceDto.getNetTotal());
            invoice.setDate(invoiceDto.getDate());
            invoice.setDiscount(invoiceDto.getDiscount());
            invoice.setDiscountAmount(invoiceDto.getDiscountAmount());
            invoice.setGrandTotal(invoiceDto.getGrandTotal());
            invoice.setDueAmount(invoiceDto.getDueAmount());

            for (ItemInfoEntity item : invoiceDto.getItems()
            ) {
                ItemInfoEntity itemInfo = itemInfoRepository.findById(item.getId()).stream().findFirst().get();
                invoiceItems.add(itemInfo);
            }
            invoice.setItems(invoiceItems);
            invoiceRepository.save(invoice);
        } catch (RuntimeException ex) {
            logger.error("User not found : " + InvoiceService.class);
            throw ex;
        }

        return new TransactionResponse("Bill successfully created");
    }

    public List<InvoiceEntity> findInvoicesByGivenDateRange(String startDate, String endDate) {
        return invoiceRepository.getInvoiceBetweenDates(startDate,endDate);
    }

    public InvoiceEntity findInvoiceByOrderNo(Integer orderNo) {
        return invoiceRepository.findByOrderNo(orderNo);
    }

    public List<InvoiceEntity> findInvoiceByCustomerName(String customerName) {
        return invoiceRepository.findAllByCustomerName(customerName);
    }

    public InvoiceDto getBillById(Integer id) {
        Optional<ItemInfoEntity> byId = itemInfoRepository.findById(id);
        if (byId.isPresent()) {
            return modelMapper.map(byId.get(), InvoiceDto.class);
        }
        return new InvoiceDto();
    }

}
