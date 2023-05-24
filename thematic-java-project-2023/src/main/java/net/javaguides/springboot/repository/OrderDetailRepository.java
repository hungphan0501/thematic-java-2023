package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.OrderDetail;
import net.javaguides.springboot.model.Product;
import net.javaguides.springboot.model.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    List<OrderDetail> getAllByIdOrder(int idOrder);

    @Query("SELECT p FROM ProductDetail  p JOIN OrderDetail o ON p.id=o.idProductDetail WHERE o.idProductDetail=?1")
    ProductDetail getProductDetailById(int id);


    @Query("SELECT p FROM Product  p JOIN ProductDetail pd ON p.id=pd.idProduct WHERE pd.id=?1")
    Product getProductByIdProductDetail(int id);

}
