package com.programm.service_db.Repository;

import com.programm.service_db.Model.Entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase,Long> {

}
