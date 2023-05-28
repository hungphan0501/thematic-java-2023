package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.*;
import net.javaguides.springboot.repository.*;
import net.javaguides.springboot.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class CartController {

    @Autowired
    CartService cartService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductDetailRepository productDetailRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    BrandRepository brandRepository;

    public Product getProductByIdDetail(int idDetail) {
        ProductDetail productDetail =  productDetailRepository.getProductDetailById(idDetail);
        Product product = productRepository.getProductById(productDetail.getIdProduct());
        return product;
    }

    public ProductDetail getProductDetailById(int idDetail) {
        return productDetailRepository.getProductDetailById(idDetail);
    }
    @GetMapping("/cart")
    public String cartPage(Model model) {
        int idUser = 0;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username);
            if (user == null) {
                return "redirect:/login";
            }
            idUser = user.getId();
        }
        List<Cart> cartList = cartService.getAllProductInCartOfCustomer(idUser);
        double totalPrice = 0;
        if(cartList!= null){
            for (Cart cart : cartList) {
                totalPrice += cart.getTotalPrice();
            }
        }
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("carts",cartList);
        return "user/cart";

    }

    @PostMapping("/addToCart/idDetail/{idDetail}/quantity/{quantity}")
    public RedirectView addToCart(@PathVariable("idDetail") int idDetail, @PathVariable("quantity") int quantity) {
        System.out.println("id: " + idDetail + "\tquantity: " + quantity);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username);
            if (user != null) {
                Optional<Cart> optionalCart = cartService.findByUserIdAndProductDetailId(user.getId(), idDetail);
                Optional<ProductDetail> optionalProductDetail = productDetailRepository.findById(idDetail);
                if (optionalProductDetail.isPresent()) {
                    ProductDetail productDetail = optionalProductDetail.get();
                    Optional<Product> optionalProduct = productRepository.findById(productDetail.getIdProduct());
                    Product product = optionalProduct.get();
                    double totalPrice = (product.getPrice() * (100-product.getSaleRate())/100) * quantity;
                    Cart cart;
                    if (optionalCart.isPresent()) {
                        cart = optionalCart.get();
                        cart.setQuantity(cart.getQuantity() + quantity);
                        cart.setTotalPrice(cart.getTotalPrice() + totalPrice);
                    } else {
                        cart = new Cart();
                        cart.setIdCustomer(user.getId());
                        cart.setIdProductDetail(idDetail);
                        cart.setQuantity(quantity);
                        cart.setTotalPrice(totalPrice);
                    }
                    cartService.addCart(cart);
                } else {
                    return new RedirectView("/login");
                }
            }
        } else {
            return new RedirectView("/login");
        }
        return new RedirectView("/cart");
    }

    public Category getCategory(int id) {
        return categoryRepository.getCategoryById(id);
    }
    public Brand getBrand(int id) {
        return brandRepository.getBrandById(id);
    }

    @PostMapping("/cart/update")
    public String updateCarts(@RequestParam("idCart") int idCart, @RequestParam("quantity") int quantity,RedirectView redirectView) {
        Optional<Cart> cart = cartService.findById(idCart);
        if (cart.isPresent()) {
            Cart cart1 = cart.get();
            double price = cart1.getTotalPrice() / cart1.getQuantity();
            cart1.setQuantity(quantity);
            cart1.setTotalPrice(quantity * price);
            cartRepository.save(cart1);
            redirectView.addStaticAttribute("message", "Cập nhật giỏ hàng thành công!");
        }
        return "redirect:/cart";
    }
    @RequestMapping(value = "/cart/remove/{idCart}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String removeCart(@PathVariable("idCart") int idCart,RedirectView redirectView) {
        cartService.removeCart(idCart);
        redirectView.addStaticAttribute("message", "Sản phẩm đã được xóa");
        return "redirect:/cart";
    }
}
