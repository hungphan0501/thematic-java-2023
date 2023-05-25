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

    public Product getProductByIdDetail(int idDetail) {
        ProductDetail productDetail =  productDetailRepository.getProductDetailById(idDetail);
        Product product = productRepository.getProductById(productDetail.getIdProduct());
        return product;
    }

    @GetMapping("/cart")
    public String cartPage(Model model) {
        int idUser = 0;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username);
            idUser = user.getId();
        }
        List<Cart> cartList = cartService.getAllProductInCartOfCustomer(idUser);
        model.addAttribute("carts",cartList);
        return "user/cart";

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
