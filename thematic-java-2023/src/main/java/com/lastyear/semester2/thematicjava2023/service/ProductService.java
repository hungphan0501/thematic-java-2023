package com.lastyear.semester2.thematicjava2023.service;

import com.lastyear.semester2.thematicjava2023.model.Product;
import com.lastyear.semester2.thematicjava2023.repository.ProductRepository;
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


}
