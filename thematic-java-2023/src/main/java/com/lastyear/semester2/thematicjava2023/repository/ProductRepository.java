package com.lastyear.semester2.thematicjava2023.repository;

import com.lastyear.semester2.thematicjava2023.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    //    @Query("SELECT p.id, p.brand, p.name, p.category, p.price, p.sale_rate,p.star_rate, p.description,p.total_value, p.sole_value, p.Active, p.create_at, FROM product p INNER JOIN linkimg l ON p.id=l.idProduct WHERE l.level=0 AND p.id=?1")
    @Query("SELECT p FROM Product p WHERE p.brand=?1")
    public List<Product> findByBrand(int idBrand);

    @Query("SELECT p FROM Product p WHERE p.category=?1")
    public List<Product> findByCategory(int idCategory);

    @Query("SELECT p FROM Product p WHERE p.price BETWEEN ?1 AND ?2 ORDER BY p.price ASC")
    public List<Product> findByPrices(int belowPrice, int abovePrice);
}
