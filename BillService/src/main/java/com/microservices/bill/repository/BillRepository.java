package com.microservices.bill.repository;

import com.microservices.bill.entity.Bill;
import org.springframework.data.repository.CrudRepository;

public interface BillRepository extends CrudRepository<Bill, Long> {

}
