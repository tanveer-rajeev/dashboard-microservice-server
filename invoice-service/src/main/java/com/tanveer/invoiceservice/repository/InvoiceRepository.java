package com.tanveer.invoiceservice.repository;

import com.tanveer.invoiceservice.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity,Integer> {
    InvoiceEntity findByOrderNo(Integer orderNo);
    List<InvoiceEntity> findAllByCustomerName(String customerName);

    @Query(value = "select * from invoice m where  m.date between :dateStart and :dateEnd order by date desc",nativeQuery = true)
    List<InvoiceEntity> getInvoiceBetweenDates(
            @Param("dateStart") String dateStart,
            @Param("dateEnd") String dateEnd
    );
}
