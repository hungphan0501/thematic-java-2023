package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Cart;
import net.javaguides.springboot.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;

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
}
