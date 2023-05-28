package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.Cart;
import net.javaguides.springboot.model.Product;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.BrandRepository;
import net.javaguides.springboot.repository.CartRepository;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    BrandRepository brandRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartRepository cartRepository;


    @GetMapping("/")
    public List<Product> getAllProduct() {
        List<Product> list = productService.getAllProduct();
        for (Product p : list) {
            System.out.println(p.toString());
        }
        return productService.getAllProduct();
    }

    @GetMapping("/brand/{idBrand}")
    public String findByBrand(@PathVariable("idBrand") int idBrand, Model model) {
        System.out.println("id brand " + idBrand);
        List<Product> list = productService.finByBrand(idBrand);
        String nameBrand = findBrandById(idBrand);
        for (Product p : list) {
            System.out.println(p.toString());
        }
        double totalPrice = 0;
        List<Cart> carts = getCartsOfUser();
        if(carts!= null){
            for (Cart cart : carts) {
                totalPrice += cart.getTotalPrice();
            }
        }
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("carts", carts);
        model.addAttribute("brandName", nameBrand);
        model.addAttribute("products", list);
        return "user/category";
    }
    public List<Cart> getCartsOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username);
            if(user != null){
                return cartRepository.getAllProductInCartOfCustomer(user.getId());
            }
            return null;

        } else return null;

    }

    public String findBrandById(int idBrand){
        return brandRepository.getNameById(idBrand);
    }

    @GetMapping("/category/{idCategory}")
    public String findByCategory(@PathVariable("idCategory") int idCategory, Model model) {
        List<Product> list = productService.finByCategory(idCategory);
        for (Product p : list) {
            System.out.println(p.toString());
        }
        double totalPrice = 0;
        List<Cart> carts = getCartsOfUser();
        if(carts!= null){
            for (Cart cart : carts) {
                totalPrice += cart.getTotalPrice();
            }
        }
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("carts", carts);
        model.addAttribute("products",list);
        return "user/category";
    }

    @GetMapping("/price/{belowPrice}/{abovePrice}")
    public List<Product> findByCategory(@PathVariable("belowPrice") int belowPrice, @PathVariable("abovePrice") int abovePrice) {
        List<Product> list = productService.finByPrices(belowPrice, abovePrice);
        System.out.println("Price 1: " + abovePrice + "\tPrice 2: " + belowPrice);
        for (Product p : list) {
            System.out.println(p.toString());
        }
        return list;
    }

    //lọc sản phẩm
    @GetMapping("/filter")
    public String filterProduct(@RequestParam("brands") String brand,
                                @RequestParam("sizes") String size,
                                @RequestParam("price-sort") int priceSort, Model model) {

        String[] sizeStrings = size.split("/");
        List<Integer> sizes = Arrays.stream(sizeStrings)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        String[] brandStrings = brand.split("/");
        List<Integer> brands = Arrays.stream(brandStrings)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        System.out.println("brand[] " +brands + "\tsizes[] " +sizes);
        List<Product> list = null;
        if (!sizes.isEmpty() && !brands.isEmpty()) {
            if (priceSort == 1) {
                list = productService.filterProductsDesc(sizes, brands);
            } else {
                list = productService.filterProductsAsc(sizes, brands);
            }
        } else if (!brands.isEmpty()) {
            list = productService.filterProductsByBrandAndPrice(brands);
            if (priceSort == 0) {
                Collections.reverse(list);
            }
        } else if (!sizes.isEmpty()) {
            list = productService.filterProductsExceptBrand(sizes);
            if (priceSort == 0) {
                Collections.reverse(list);
            }
        } else {
            list = productService.filterProductsBYPrice();
            if (priceSort == 0) {
                Collections.reverse(list);
            }
        }
        List<Cart> cartList = getCartsOfUser();
        double totalPrice = 0;
        if(cartList!= null){
            for (Cart cart : cartList) {
                totalPrice += cart.getTotalPrice();
            }
        }
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("carts",cartList);
        model.addAttribute("products", list);
        return "user/category";
    }




}
