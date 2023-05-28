package net.javaguides.springboot.paypal;

import com.paypal.api.payments.*;
import com.paypal.base.rest.PayPalRESTException;
import net.javaguides.springboot.model.Cart;
import net.javaguides.springboot.model.OrderDetail;
import net.javaguides.springboot.model.Orders;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.CartRepository;
import net.javaguides.springboot.repository.OrderDetailRepository;
import net.javaguides.springboot.repository.OrdersRepository;
import net.javaguides.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/payment/paypal")
public class PaypalController {

    @Autowired
    private PaypalService paypalService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    CartRepository cartRepository;

    @PostMapping("/pay")
    public RedirectView pay(@RequestParam("cartIds") String cartIds,@RequestParam("idAddress") int idAddress,HttpServletRequest request) {
        RedirectView redirectView = new RedirectView();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username);
            if (user == null) {
                 redirectView.setUrl("/login");
                return redirectView;
            }
        }
        String[] listCartId = cartIds.split("/");
        List<Cart> cartList = new ArrayList<>();
        for (String id : listCartId) {
            int idC = Integer.parseInt(id);
            Optional<Cart> cart = cartRepository.findById(idC);
            cartList.add(cart.get());
        }
        try {
            String cancelUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath() + "/payment/paypal/cancel";
            String successUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath() + "/payment/paypal/success";

            // create payment

            Payment payment = paypalService.createPayment(cartList,idAddress, "USD", cancelUrl, successUrl);
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    redirectView.setUrl(link.getHref());
                    return redirectView;
                }
            }
        } catch (PayPalRESTException e) {
            // handle exception
        }
        redirectView.setUrl("/payment/paypal/cancel");
        return redirectView;
    }

    @GetMapping("/success")
    public RedirectView success(@RequestParam("paymentId") String paymentId,
                                          @RequestParam("PayerID") String payerId) {
        RedirectView redirectView = new RedirectView();
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                // payment success, do something here
                List<Cart> cartList =getCartItems();
                int idUser = 0;
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null && authentication.isAuthenticated()) {
                    String username = authentication.getName();
                    User user = userRepository.findByEmail(username);
                    idUser = user.getId();
                }
                double totalPrice = 0;
                for (Cart cart : cartList) {
                    totalPrice += cart.getTotalPrice();
                }
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String createAt = now.format(formatter);
                Date date = Date.valueOf(createAt);
                System.out.println("createAt : " + createAt + "\tdate: " +date);

                Orders orders = new Orders(idUser, totalPrice, date, getIdAddress(), "Chờ xác nhận", "Paypal", "Đã thanh toán");
                ordersRepository.save(orders);
                for (Cart cart : cartList) {
                    cartRepository.delete(cart);
                    OrderDetail orderDetail = new OrderDetail(orders.getId(), cart.getQuantity(), cart.getIdProductDetail());
                    orderDetailRepository.save(orderDetail);

                }
                String url = "/user/history/" + orders.getId();
                redirectView.setUrl(url);
                return redirectView;

            }
        } catch (PayPalRESTException e) {
            // handle exception
        }
        redirectView.setUrl("/user/infor");
        return redirectView;
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> cancel() {
        // handle cancel payment
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment cancelled");
    }

    public List<Cart> getCartItems() {
        // Gọi phương thức getCartItems() của PaypalService để lấy danh sách Cart
        return paypalService.getCartItems();
    }

    public int getIdAddress() {
        return paypalService.getIdAddress();
    }

}

