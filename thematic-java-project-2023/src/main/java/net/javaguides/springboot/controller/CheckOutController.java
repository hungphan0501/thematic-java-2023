package net.javaguides.springboot.controller;


import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import net.javaguides.springboot.model.*;
import net.javaguides.springboot.repository.OrderDetailRepository;
import net.javaguides.springboot.repository.OrdersRepository;
import net.javaguides.springboot.repository.ProductDetailRepository;
import net.javaguides.springboot.repository.ProductRepository;
import net.javaguides.springboot.service.ProductService;
import net.javaguides.springboot.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/checkout")
public class CheckOutController {
    String clientId = "AROAKMekTQJgIeU7-SdhWlX1VMRbAmBiAclM6cUY753HNXchrLmLfz1Z0NntWJuivqxdi2vGpYKw0w_P";
    String clientSecret = "EDVHtLQaWGIFJOqsUpqWYgWXSeChoY_0dD3OUu_wsSc-RDgNoDcrfvCnN8uXEyfLEWzPhOIPEOvQb_hY";

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductDetailRepository productDetailRepository;

    //thanh toan khi nhan hang
    @PostMapping("/onDelivery")
    public ResponseEntity<String> paymentOnDelivery(@RequestBody List<Cart> cartList, @RequestParam("idAddress") int idAddress){
        int idUser = userService.getIdUserByUserName();

        double totalPrice = 0;
        for( Cart cart: cartList) {
            totalPrice += cart.getTotalPrice();
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String createAt = now.format(formatter);
        Date date = Date.valueOf(createAt);
        System.out.println("createAt : " + createAt + "\tdate: " +date);

        Orders orders = new Orders(idUser,totalPrice,date,idAddress,"Đã đặt hàng","Thanh toán khi nhận hàng","Chưa thanh toán");
        ordersRepository.save(orders);
        for( Cart cart: cartList) {
            OrderDetail orderDetail = new OrderDetail(orders.getId(),cart.getQuantity(),cart.getIdProductDetail());
            orderDetailRepository.save(orderDetail);
        }
        return ResponseEntity.ok("Order successfully");
    }

//    //thanh toan khi nhan hang
//    @GetMapping("/onDelivery1")
//    public ResponseEntity<String> paymentOnDelivery1( @RequestParam("idAddress") int idAddress){
//        int idUser = userService.getIdUserByUserName();
//        System.out.println("IdUser: " +idUser);
//
//        Cart c1 = new Cart(idUser, 3 ,3 ,180.0);
//        Cart c2 = new Cart(idUser, 10 ,12 ,720.0);
//        Cart c3 = new Cart(idUser, 20 ,2 ,180.0);
//        List<Cart> cartList = new ArrayList<>();
//        cartList.add(c1);
//        cartList.add(c2);
//        cartList.add(c3);
//
//        double totalPrice = 0;
//        for( Cart cart: cartList) {
//            totalPrice += cart.getTotalPrice();
//        }
//        LocalDateTime now = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy: HH:mm:ss");
//        String createAt = now.format(formatter);
//        System.out.println("createAt : "+createAt);
//
//        Orders orders = new Orders(idUser,totalPrice,createAt,idAddress,"Đã đặt hàng","Thanh toán khi nhận hàng","Chưa thanh toán");
//        ordersRepository.save(orders);
//        for( Cart cart: cartList) {
//            OrderDetail orderDetail = new OrderDetail(orders.getId(),cart.getQuantity(),cart.getIdProductDetail());
//            orderDetailRepository.save(orderDetail);
//        }
//        return ResponseEntity.ok("Order successfully");
//    }


}
