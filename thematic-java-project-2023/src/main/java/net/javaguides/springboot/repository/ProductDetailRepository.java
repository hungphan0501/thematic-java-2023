package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Product;
import net.javaguides.springboot.model.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail,Integer> {

    @Query("SELECT p FROM ProductDetail p WHERE p.idProduct=?1")
    public List<ProductDetail> getAllProductDetailByIdProduct(int idProduct);
    @Query("SELECT p FROM ProductDetail p WHERE p.color=?1")
    public List<ProductDetail> getAllProductDetailByColor(String color);

    ProductDetail getProductDetailById(int id);


}
