package com.programm.service_db.Repository;

import com.programm.service_db.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Product,Long> {
}
