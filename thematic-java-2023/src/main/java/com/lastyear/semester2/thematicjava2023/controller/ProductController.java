package com.lastyear.semester2.thematicjava2023.controller;

import com.lastyear.semester2.thematicjava2023.model.Product;
import com.lastyear.semester2.thematicjava2023.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService productService;


    @GetMapping("/listAllProduct")
    public List<Product> getAllProduct() {
        List<Product> list =productService.getAllProduct();
        for(Product p : list) {
            System.out.println(p.toString());
        }
        return productService.getAllProduct();
    }
}
