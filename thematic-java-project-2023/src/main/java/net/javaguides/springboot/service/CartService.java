package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Cart;
import net.javaguides.springboot.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public List<Cart> getAllProductInCartOfCustomer(int idCustomer) {
        return cartRepository.getAllProductInCartOfCustomer(idCustomer);
    }

    public Cart addCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Optional<Cart> findByUserIdAndProductDetailId(int idUser, int idProductDetail) {
        return cartRepository.findByUserIdAndProductDetailId(idUser,idProductDetail);
    }

    public Optional<Cart> getCartById(int id) {
        return cartRepository.findById(id);
    }

    @Transactional
    public void deleteCartById(int id) {
        cartRepository.deleteCartById(id);
    }
    public Optional<Cart> findById(int id) {
        return cartRepository.findById(id);
    }



    @Transactional
    public void updateCart(int quantity, double totalPrice, int id) {
        Query query = entityManager.createQuery("UPDATE Cart c SET c.quantity = :quantity, c.totalPrice = :totalPrice WHERE c.id = :id");
        query.setParameter("quantity", quantity);
        query.setParameter("totalPrice", totalPrice);
        query.setParameter("id", id);
        query.executeUpdate();
    }

}
