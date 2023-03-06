package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Cart;
import net.javaguides.springboot.model.Product;
import net.javaguides.springboot.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {
    @Query("SELECT c FROM Cart c WHERE c.idCustomer=?1")
    public List<Cart> getAllProductInCartOfCustomer(int idCustomer);

    @Query("SELECT c FROM Cart c WHERE c.idCustomer=?1 AND c.idProductDetail=?2")
    public Optional<Cart> findByUserIdAndProductDetailId(int idCustomer,int idProductDetail);

}
