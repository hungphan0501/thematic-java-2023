package com.lastyear.semester2.thematicjava2023.repository;

import com.lastyear.semester2.thematicjava2023.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
//    @Query('{}')
}
