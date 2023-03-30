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

    @PostMapping("/paypal")
    public ResponseEntity<String> checkoutByPayPal (@RequestBody List<Cart> cartList,@RequestParam("phone") String phone, @RequestParam("address") String address) {
        // Lấy danh sách sản phẩm từ giỏ hàng
        List<Item> payPalItems = new ArrayList<>();

        double total = 0;
        for(Cart cart : cartList) {
            // Lấy thông tin sản phẩm từ Cart
            Optional<ProductDetail> productDetail = productDetailRepository.findById(cart.getIdProductDetail());
            ProductDetail productDetail1 = productDetail.get();
            Optional<Product> p = productRepository.findById(productDetail1.getIdProduct());
            Product product = p.get();
            int quantity = cart.getQuantity();
            double price = cart.getTotalPrice() / quantity;

            // Tạo một sản phẩm mới
            Item payPalItem = new Item();
            payPalItem.setName(product.getName());
            payPalItem.setQuantity(Integer.toString(quantity));
            payPalItem.setPrice(Double.toString(price));
            payPalItem.setCurrency("USD");
            payPalItems.add(payPalItem);

            // Tính tổng số tiền của đơn hàng
            total += cart.getTotalPrice();
        }

        // Tạo các thông tin về giá tiền và thanh toán
        Amount amount = new Amount();
        amount.setTotal(Double.toString(total));
        amount.setCurrency("USD");
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction();
        transaction.setDescription("Thanh toán mua hàng");
        transaction.setAmount(amount);
        transaction.setItemList(new ItemList().setItems(payPalItems));
        transactions.add(transaction);

        // Tạo một đối tượng Payment để gửi yêu cầu tạo đơn hàng đến PayPal
        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(new Payer().setPaymentMethod("paypal"));
        payment.setTransactions(transactions);
        payment.setRedirectUrls(new RedirectUrls().setCancelUrl("http://localhost:8080/checkout/cancel").setReturnUrl("http://localhost:8080/checkout/return"));
        // Kết nối đến PayPal và tạo đơn hàng
        try {
            APIContext apiContext = new APIContext(new OAuthTokenCredential(clientId, clientSecret).getAccessToken());
            Payment createdPayment = payment.create(apiContext);

            // Lưu Order vào CSDL
            int idUser = userService.getIdUserByUserName();

            double totalPrice = 0;
            for( Cart cart: cartList) {
                totalPrice += cart.getTotalPrice();
            }

            Orders orders = new Orders(idUser,totalPrice,"",address,phone,"Đã đặt hàng","Paypal","Đã thanh toán");
            ordersRepository.save(orders);
            for( Cart cart: cartList) {
                OrderDetail orderDetail = new OrderDetail(orders.getId(),cart.getQuantity(),cart.getIdProductDetail());
                orderDetailRepository.save(orderDetail);
            }

            // Redirect đến trang thanh toán của PayPal
            for(Links link : createdPayment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return ResponseEntity.status(HttpStatus.OK).body(link.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Thanh toán thất bại");
        }

        // Hiển thị thông báo lỗi cho người dùng
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Thanh toán thất bại");

    }

    @GetMapping("/cancel")
    public String cancel () {
        return "cancel";
    }

    @GetMapping("/return")
    public String returnWeb () {
        return "return";
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

    @GetMapping("/paypal1")
    public ResponseEntity<String> checkoutByPayPal1 (@RequestParam("phone") String phone, @RequestParam("address") String address) {
        // Lấy danh sách sản phẩm từ giỏ hàng
        int idUser = userService.getIdUserByUserName();

        List<Item> payPalItems = new ArrayList<>();
        Cart c1 = new Cart(idUser, 3 ,3 ,180.0);
        Cart c2 = new Cart(idUser, 10 ,12 ,720.0);
        Cart c3 = new Cart(idUser, 20 ,2 ,180.0);
        List<Cart> cartList = new ArrayList<>();
        cartList.add(c1);
        cartList.add(c2);
        cartList.add(c3);

        double total = 0;
        for(Cart cart : cartList) {
            // Lấy thông tin sản phẩm từ Cart
            Optional<ProductDetail> productDetail = productDetailRepository.findById(cart.getIdProductDetail());
            ProductDetail productDetail1 = productDetail.get();
            Optional<Product> p = productRepository.findById(productDetail1.getIdProduct());
            Product product = p.get();
            int quantity = cart.getQuantity();
            double price = cart.getTotalPrice() / quantity;

            // Tạo một sản phẩm mới
            Item payPalItem = new Item();
            payPalItem.setName(product.getName());
            payPalItem.setQuantity(Integer.toString(quantity));
            payPalItem.setPrice(Double.toString(price));
            payPalItem.setCurrency("USD");
            payPalItems.add(payPalItem);

            // Tính tổng số tiền của đơn hàng
            total += cart.getTotalPrice();
        }

        // Tạo các thông tin về giá tiền và thanh toán
        Amount amount = new Amount();
        amount.setTotal(Double.toString(total));
        amount.setCurrency("USD");
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction = new Transaction();
        transaction.setDescription("Thanh toán mua hàng");
        transaction.setAmount(amount);
        transaction.setItemList(new ItemList().setItems(payPalItems));
        transactions.add(transaction);

        // Tạo một đối tượng Payment để gửi yêu cầu tạo đơn hàng đến PayPal
        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(new Payer().setPaymentMethod("paypal"));
        payment.setTransactions(transactions);
        payment.setRedirectUrls(new RedirectUrls().setCancelUrl("http://localhost:8080/checkout/cancel").setReturnUrl("http://localhost:8080/checkout/return"));
        // Kết nối đến PayPal và tạo đơn hàng
        try {
            APIContext apiContext = new APIContext(new OAuthTokenCredential(clientId, clientSecret).getAccessToken());
            Payment createdPayment = payment.create(apiContext);

            // Lưu Order vào CSDL

            Orders orders = new Orders(idUser,total,"",address,phone,"Đã đặt hàng","Paypal","Đã thanh toán");
            ordersRepository.save(orders);
            for( Cart cart: cartList) {
                OrderDetail orderDetail = new OrderDetail(orders.getId(),cart.getQuantity(),cart.getIdProductDetail());
                orderDetailRepository.save(orderDetail);
            }

            // Redirect đến trang thanh toán của PayPal
            for(Links link : createdPayment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return ResponseEntity.status(HttpStatus.OK).body(link.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Thanh toán thất bại");
        }

        // Hiển thị thông báo lỗi cho người dùng
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Thanh toán thất bại");

    }
}
