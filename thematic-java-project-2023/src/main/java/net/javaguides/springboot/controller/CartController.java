package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.Cart;
import net.javaguides.springboot.model.Product;
import net.javaguides.springboot.model.ProductDetail;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.ProductDetailRepository;
import net.javaguides.springboot.repository.ProductRepository;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductDetailRepository productDetailRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/cartById/{id}")
    public Cart getCartById(@PathVariable int id) {
        return cartService.getCartById(id).get();
    }

    @GetMapping("/allProductInCart/{idCustomer}")
    public ResponseEntity<List<Cart>> allProductInCart(@PathVariable("idCustomer") int idCustomer) {
        try {
            List<Cart> list = cartService.getAllProductInCartOfCustomer(idCustomer);
            if (list.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<List<Cart>>(list, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addCart1")
    public ResponseEntity<Cart> addToCart2(@RequestBody Cart cart) {
        System.out.println("Vào rồi!");
        try {
            if (cart == null) {
                System.out.println("Cart is null!");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            cart.updatePrice(productDetailRepository, productRepository);
            cartService.addCart(cart);
            System.out.println(cart.toString());
            return new ResponseEntity<Cart>(cart, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/addCart/{idProductDetail}/{quantity}")
    public String addToCart(@PathVariable("idProductDetail") int idProductDetail, @PathVariable("quantity") int quantity, Principal principal) {
        System.out.println("Vào rồi!"); 
        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
//                return "redirect:/login"; // chuyển hướng về trang đăng nhập nếu chưa đăng nhập
            if (principal == null) {
                return "redirect:/login"; // Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập
            } else {
                String userEmail = principal.getName();
                User user = userRepository.findByEmail(userEmail);
                int idCustomer = user.getId();
                Optional<Cart> optionalCart = cartService.findByUserIdAndProductDetailId(idCustomer, idProductDetail);
                Optional<ProductDetail> optionalProductDetail = productDetailRepository.findById(idProductDetail);

                if (optionalProductDetail.isPresent()) {
                    ProductDetail productDetail = optionalProductDetail.get();
                    Optional<Product> optionalProduct = productRepository.findById(productDetail.getIdProduct());
                    Product product = optionalProduct.get();
                    double totalPrice = product.getPrice() * quantity;
                    Cart cart;
                    if (optionalCart.isPresent()) {
                        cart = optionalCart.get();
                        cart.setQuantity(cart.getQuantity() + quantity);
                        cart.setTotalPrice(cart.getTotalPrice() + totalPrice);
                    } else {
                        cart = new Cart();
                        cart.setIdCustomer(idCustomer);
                        cart.setIdProductDetail(idProductDetail);
                        cart.setQuantity(quantity);
                        cart.setTotalPrice(totalPrice);
                    }
                    String idUser = String.valueOf(user.getId());
                    cartService.addCart(cart);
                    return "redirect:/cart/allProductInCart/" + idUser;
                } else {
                    return "Add Product to Cart failed!";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addProductToCart(@RequestParam("user") int idUser, @RequestParam("product") int idProductDetail, @RequestParam("quantity") int quantity) {
        System.out.println("Vào rồi!");
        Optional<Cart> optionalCart = cartService.findByUserIdAndProductDetailId(idUser, idProductDetail);
        Optional<ProductDetail> optionalProductDetail = productDetailRepository.findById(idProductDetail);

        if (optionalProductDetail.isPresent()) {
            ProductDetail productDetail = optionalProductDetail.get();
            Optional<Product> optionalProduct = productRepository.findById(productDetail.getIdProduct());
            Product product = optionalProduct.get();
//            ProductDetail productDetail = optionalProductDetail.get();
            double totalPrice = product.getPrice() * quantity;
            Cart cart;
            if (optionalCart.isPresent()) {
                cart = optionalCart.get();
                cart.setQuantity(cart.getQuantity() + quantity);
                cart.setTotalPrice(cart.getTotalPrice() + totalPrice);
            } else {
                cart = new Cart();
                cart.setIdCustomer(idUser);
                cart.setIdProductDetail(idProductDetail);
                cart.setQuantity(quantity);
                cart.setTotalPrice(totalPrice);
            }

            cartService.addCart(cart);
            return ResponseEntity.ok(cart);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // Xóa một cart dựa trên ID
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable("id") int id) {
        cartService.deleteCartById(id);
        return ResponseEntity.ok().build();
    }

    //update số lượng cart
    @GetMapping("/update/{id}/{quantity}")
    public ResponseEntity<Cart> updateCart(@PathVariable int id, @PathVariable int quantity) {
        try {
            Optional<Cart> cart = cartService.findById(id);
            if (cart.isPresent()) {
                Cart cart1 = cart.get();
                double price = cart1.getTotalPrice() / cart1.getQuantity();
                cart1.setQuantity(quantity);
                cart1.setTotalPrice(quantity * price);
                cartService.updateCart(quantity,price,id);
                return new ResponseEntity<Cart>(cart1, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }


    @GetMapping("/my-page")
    public String myPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login"; // chuyển hướng về trang đăng nhập nếu chưa đăng nhập
        }
        return "my-page";
    }
}
