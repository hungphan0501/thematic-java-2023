//package net.javaguides.springboot.paypal;
//
//import com.paypal.api.payments.Links;
//import com.paypal.api.payments.Payment;
//import com.paypal.base.rest.PayPalRESTException;
//import net.javaguides.springboot.model.Cart;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/checkout")
//public class PaypalController {
//
//    List<Cart> listCartCheckOut;
//
//    @Autowired
//    PaypalService service;
////	@Autowired
////	UserProductService userProductService;
//
//
//    //	public static final String SUCCESS_URL = "pay/success";
//// 	public static final String SUCCESS_URL1 = "order1/{id_user}/{id_product}";
//    public static final String SUCCESS_URL = "/checkout/order";
//
//    public static final String CANCEL_URL = "/checkout/pay/cancel";
//
//    @GetMapping("/pay")
//    public String home() {
//        return "pay/home";
//    }
//
//    public boolean checkPrice(double orderPrice) {
//        int standardPrice = 0;
//        for (Cart cart : listCartCheckOut) {
//            standardPrice += cart.getTotalPrice();
//        }
//        if (standardPrice != orderPrice) {
//            return false;
//        }
//        return true;
//    }
//
//    //@ModelAttribute("order")
//    @PostMapping("/pay")
//    public String payment(@ModelAttribute("order") OrderNew order) {
//        System.out.println(order.toString());
//        try {
//			if(!checkPrice( order.getPrice())){
//				return " price not correct";
//			}
//            Payment payment = service.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
//                    order.getIntent(), order.getDescription(), "http://localhost:8080/" + CANCEL_URL,
//                    "http://localhost:8080/" + SUCCESS_URL + "/" + order.getIdUser() + "/" + order.getIdProduct());
//            for (Links link : payment.getLinks()) {
//                if (link.getRel().equals("approval_url")) {
//                    return "redirect:" + link.getHref();
//                }
//            }
//
//        } catch (PayPalRESTException e) {
//
//            e.printStackTrace();
//        }
//        return "redirect:/";
//    }
//
//    @GetMapping(value = CANCEL_URL)
//    public String cancelPay() {
//        return "cancel";
//    }
//    //@ModelAttribute("order") OrderNew order,
////		@GetMapping(value = SUCCESS_URL1)
////	    public String successPay(@PathVariable("id_user")String idUser,@PathVariable("id_product")String idProduct,@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
////	        try {
////	            Payment payment = service.executePayment(paymentId, payerId);
////	            System.out.println(payment.toJSON());
////					System.out.println("ID USER: "+idUser);
////				System.out.println();
////				System.out.println();
////	            if (payment.getState().equals("approved")) {
////	                return "order1/"+idUser+"/"+idProduct;
//////					return "success";
////	            }
////	        } catch (PayPalRESTException e) {
////	         System.out.println(e.getMessage());
////	        }
////	        return "redirect:/";
////	    }
//
//}
