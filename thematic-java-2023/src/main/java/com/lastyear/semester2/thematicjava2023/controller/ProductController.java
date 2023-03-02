package com.lastyear.semester2.thematicjava2023.controller;

import com.lastyear.semester2.thematicjava2023.model.Product;
import com.lastyear.semester2.thematicjava2023.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;


    @GetMapping("/")
    public List<Product> getAllProduct() {
        List<Product> list = productService.getAllProduct();
        for (Product p : list) {
            System.out.println(p.toString());
        }
        return productService.getAllProduct();
    }

    @GetMapping("/brand/{idBrand}")
    public List<Product> findByBrand(@PathVariable("idBrand") int idBrand) {
        System.out.println("id brand đâu thèn ngu này" + idBrand);
        List<Product> list = productService.finByBrand(idBrand);
        for (Product p : list) {
            System.out.println(p.toString());
        }
        return list;
    }

    @GetMapping("/category/{idCategory}")
    public List<Product> findByCategory(@PathVariable("idCategory") int idCategory) {
        List<Product> list = productService.finByCategory(idCategory);
        for (Product p : list) {
            System.out.println(p.toString());
        }
        return list;
    }

    @GetMapping("/price/{belowPrice}/{abovePrice}")
    public List<Product> findByCategory(@PathVariable("belowPrice") int belowPrice, @PathVariable("abovePrice") int abovePrice) {
        List<Product> list = productService.finByPrices(belowPrice, abovePrice);
        System.out.println("Price 1: " +abovePrice + "\tPrice 2: "+belowPrice);
        for (Product p : list) {
            System.out.println(p.toString());
        }
        return list;
    }

}
