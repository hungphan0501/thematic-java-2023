package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.Cart;
import net.javaguides.springboot.model.OrderDetail;
import net.javaguides.springboot.model.Orders;
import net.javaguides.springboot.repository.OrderDetailRepository;
import net.javaguides.springboot.repository.OrdersRepository;
import net.javaguides.springboot.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/checkout")
public class CheckOutController {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/paypal")
    public ResponseEntity<String> checkoutByPayPal (@RequestBody List<Cart> cartList) {
        return ResponseEntity.ok("");
    }

    //thanh toan khi nhan hang
    @PostMapping("/onDelivery")
    public ResponseEntity<String> paymentOnDelivery(@RequestBody List<Cart> cartList,@RequestParam("phone") String phone, @RequestParam("address") String address){
        int idUser = userService.getIdUserByUserName();

        double totalPrice = 0;
        for( Cart cart: cartList) {
            totalPrice += cart.getTotalPrice();
        }

        Orders orders = new Orders(idUser,totalPrice,"",address,phone,"Đã đặt hàng","Thanh toán khi nhận hàng","Chưa thanh toán");
        ordersRepository.save(orders);
        for( Cart cart: cartList) {
            OrderDetail orderDetail = new OrderDetail(orders.getId(),cart.getQuantity(),cart.getIdProductDetail());
            orderDetailRepository.save(orderDetail);
        }
        return ResponseEntity.ok("Order successfully");
    }

//    //thanh toan khi nhan hang
//    @GetMapping("/onDelivery1")
//    public ResponseEntity<String> paymentOnDelivery1(@RequestParam("phone") String phone, @RequestParam("address") String address){
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
//
//        Orders orders = new Orders(idUser,totalPrice,"",address,phone,"Đã đặt hàng","Thanh toán khi nhận hàng","Chưa thanh toán");
//        ordersRepository.save(orders);
//        for( Cart cart: cartList) {
//            OrderDetail orderDetail = new OrderDetail(orders.getId(),cart.getQuantity(),cart.getIdProductDetail());
//            orderDetailRepository.save(orderDetail);
//        }
//        return ResponseEntity.ok("Order successfully");
//    }
}
