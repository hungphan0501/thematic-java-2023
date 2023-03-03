package com.lastyear.thematicjava2023.service;

import com.lastyear.thematicjava2023.model.Product;
import com.lastyear.thematicjava2023.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public List<Product> finByBrand(int idBrand) {
        return productRepository.findByBrand(idBrand);
    }

    public List<Product> finByCategory(int idCategory) {
        return productRepository.findByCategory(idCategory);
    }


    public List<Product> finByPrices(int belowPrice, int abovePrice) {
        return productRepository.findByPrices(belowPrice, abovePrice);
    }


}
