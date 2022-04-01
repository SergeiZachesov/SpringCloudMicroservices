package com.microservices.bill.service;

import com.microservices.bill.entity.Bill;
import com.microservices.bill.exeption.BillNotFoundException;
import com.microservices.bill.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class BillService {

    private BillRepository repository;

    @Autowired
    public BillService(BillRepository repository) {
        this.repository = repository;
    }

    public Bill getBillById(Long billId) {
        return repository.findById(billId).orElseThrow(() -> new BillNotFoundException("Unable to find bill with id: " + billId));
    }

    public Long createBill(Long accountId, BigDecimal amount, Boolean isDeaful, Boolean overdriveEnable) {
        Bill bill = new Bill(accountId, amount, isDeaful, OffsetDateTime.now(), overdriveEnable);
        return repository.save(bill).getBillId();
    }

    public Bill updateBill(Long billId, Long accountId, BigDecimal amount, Boolean isDeaful, Boolean overdriveEnable) {
        Bill bill = new Bill(accountId, amount, isDeaful, overdriveEnable);
        bill.setBillId(billId);
        return repository.save(bill);
    }

    public Bill deleteBill(Long billId) {
        Bill bill = getBillById(billId);
        repository.delete(bill);
        return bill;
    }

    public List<Bill> getBillsByAccountId(Long accountId) {
        return repository.getBillsByAccountId(accountId);
    }
}
