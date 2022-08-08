package com.programm.service_db.Repository;

import com.programm.service_db.Model.Entity.Buyer;
import com.programm.service_db.Model.Entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    @Query("SELECT e.buyer.id  FROM Purchase e WHERE e.product.id = :id GROUP BY e.buyer.id having count(e.buyer.id) >= :min")
    List<Long> findByProductAndMaxBuy(@Param("id") Long id, @Param("min") Long min);

    @Query("SELECT e.buyer.id FROM Purchase e GROUP BY e.buyer.id " +
            "having sum(e.product.price) > :min AND sum(e.product.price) < :max")
    List<Long> findInterval(@Param("min") Long min, @Param("max") Long max);

    @Query("SELECT e.buyer.id,count(e.buyer.id) FROM Purchase e GROUP BY e.buyer.id ORDER BY count(e.buyer.id) desc ")
    List<Long> findBad(@Param("count") Long count);

    @Query("SELECT e.id FROM Purchase e WHERE e.date >= :startDate AND e.date <= :endDate")
    List<Long> date_between(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
