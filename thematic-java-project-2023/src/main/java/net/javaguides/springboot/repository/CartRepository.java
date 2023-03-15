package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Cart;
import net.javaguides.springboot.model.Product;
import net.javaguides.springboot.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {
    @Query("SELECT c FROM Cart c WHERE c.idCustomer=?1")
    public List<Cart> getAllProductInCartOfCustomer(int idCustomer);

    @Query("SELECT c FROM Cart c WHERE c.idCustomer=?1 AND c.idProductDetail=?2")
    public Optional<Cart> findByUserIdAndProductDetailId(int idCustomer,int idProductDetail);

    void deleteCartById(int id);

//    @Modifying
//    @Transactional
//    @Query("UPDATE Cart c SET c.quantity = ?1, c.totalPrice = ?2 WHERE c.id = ?3")
//    void updateCart(int quantity, double totalPrice, int id);
//
//    @Modifying
//    @Query("UPDATE Cart c SET c.quantity = :quantity, c.totalPrice = :totalPrice WHERE c.id = :id")
//    void updateCart1(@Param("quantity") int quantity, @Param("totalPrice") double totalPrice, @Param("id") int id);

}
