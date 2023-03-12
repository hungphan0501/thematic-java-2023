package net.javaguides.springboot.service;

import net.javaguides.springboot.model.ProductDetail;
import net.javaguides.springboot.repository.ProductDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDetailService {
    @Autowired
    ProductDetailRepository productDetailRepository;

    public List<ProductDetail> getAllProductDetailByIdProduct(int idProduct) {
        return productDetailRepository.getAllProductDetailByIdProduct(idProduct);
    }

    public List<ProductDetail> getAllProductDetailByColor(String color) {
        return productDetailRepository.getAllProductDetailByColor(color);
    }
}
