package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.Product;
import net.javaguides.springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomePageController {

    @Autowired
    ProductService productService;

    @GetMapping("/")
    public String getHomePage(Model model) {

        List<Product> productsNew= productService.getAllProductNew();
        List<Product> productsDiscount= productService.getAllProductDiscount();
        List<Product> productsBestSeller= productService.getAllProductBestSeller();
        model.addAttribute("productsNew", productsNew);
        model.addAttribute("productsDiscount", productsDiscount);
        model.addAttribute("productsBestSeller", productsBestSeller);
        return "user/home";
    }
}
