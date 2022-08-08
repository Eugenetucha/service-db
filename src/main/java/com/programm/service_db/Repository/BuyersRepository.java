package com.programm.service_db.Repository;

import com.programm.service_db.Model.Entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BuyersRepository extends JpaRepository<Buyer, Long> {
    @Query("SELECT e.id FROM Buyer e WHERE e.surname LIKE :surname")
    List<Long> findBySurname(@Param("surname") String surname);

}
