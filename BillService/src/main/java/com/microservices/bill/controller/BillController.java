package com.microservices.bill.controller;

import com.microservices.bill.controller.dto.BillRequestDto;
import com.microservices.bill.controller.dto.BillResponseDto;
import com.microservices.bill.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BillController {

    private final BillService service;

    @Autowired
    public BillController(BillService service) {
        this.service = service;
    }

    @GetMapping("/{billId}")
    public BillResponseDto getBill(@PathVariable Long billId) {
        return new BillResponseDto(service.getBillById(billId));
    }

    @PostMapping
    public Long createBill(@RequestBody BillRequestDto billRequestDto) {
        return service.createBill(billRequestDto.getAccountId(),
                billRequestDto.getAmount(),
                billRequestDto.getIsDefault(),
                billRequestDto.getOverdraftEnable());
    }

    @PutMapping("/{billId}")
    public BillResponseDto updateBill(@PathVariable Long billId, @RequestBody BillRequestDto billRequestDto) {

        return new BillResponseDto(service.updateBill(billId,
                billRequestDto.getAccountId(),
                billRequestDto.getAmount(),
                billRequestDto.getIsDefault(),
                billRequestDto.getOverdraftEnable()));
    }

    @DeleteMapping("/{billId}")
    public BillResponseDto deleteBill(@PathVariable Long billId) {
        return new BillResponseDto(service.deleteBill(billId));
    }
}
