package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.Cart;
import net.javaguides.springboot.model.Product;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.CartRepository;
import net.javaguides.springboot.repository.ProductRepository;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomePageController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartRepository cartRepository;

    @GetMapping("/")
    public String getHomePage(Model model) {

        List<Product> productsNew = productService.getAllProductNew();
        List<Product> productsDiscount = productService.getAllProductDiscount();
        List<Product> productsBestSeller = productService.getAllProductBestSeller();
        double totalPrice = 0;
        List<Cart> carts = getCartsOfUser();
        if(carts!= null){
            for (Cart cart : carts) {
                totalPrice += cart.getTotalPrice();
            }
        }
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("carts", carts);
        model.addAttribute("productsNew", productsNew);
        model.addAttribute("productsDiscount", productsDiscount);
        model.addAttribute("productsBestSeller", productsBestSeller);
        return "user/home";
    }

    @GetMapping("/search")
    public String searchByHeader(@RequestParam("content") String content, Model model) {
        List<Product> result = new ArrayList<>();

        String[] contents = content.split(" ");
        for (String c : contents) {
            List<Product> list = productRepository.getAllByName(c);
            List<Product> list2 = productRepository.getAllByNameBrand(c);
            List<Product> list3 = productRepository.getAllByNameCategory(c);
            result.addAll(list);
            result.addAll(list2);
            result.addAll(list3);
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
        model.addAttribute("products", result);

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
}
