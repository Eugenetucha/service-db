package com.programm.service_db.Repository;

import com.programm.service_db.Entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyersRepository  extends JpaRepository<Buyer,Long> {

}