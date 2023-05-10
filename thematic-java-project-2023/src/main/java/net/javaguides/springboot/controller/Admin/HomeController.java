package net.javaguides.springboot.controller.Admin;

import net.javaguides.springboot.repository.OrdersRepository;
import net.javaguides.springboot.repository.ProductRepository;
import net.javaguides.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class HomeController {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/dashboard")
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("admin/views/dist/index");
        int countCustomer = countUserWithRoleUser();
        int countOrder = countOrdersCompleted();
        int countProduct = countProduct();
        int productSold = countProductSold();
        int ordersWaiting = countOrdersWaitingCompleted();
        double revenue = getRevenue();
        double spending = getSpending();
        double revenueBySale = getRevenueBySale();
        System.out.println("countCustomer : " +countCustomer +"\tcountOrder: "+countOrder +"\tcountProduct: " +countProduct +"\tproductSold: "+ productSold+"\trevenue: " +revenue+"\trevenueBySale: "+revenueBySale);

        Map<String, Object> model = new HashMap<>();
        model.put("countCustomer", countCustomer);
        model.put("countOrder", countOrder);
        model.put("countProduct", countProduct);
        model.put("revenue", revenue);
        model.put("spending", spending);
        model.put("revenueBySale", revenueBySale);
        model.put("productSold", productSold);
        model.put("ordersWaiting", ordersWaiting);

        mav.addAllObjects(model);
        return mav;
    }

    public int countOrdersWaitingCompleted() {
        int result = ordersRepository.countOrdersWaitingCompleted();
        if (result > 0) {
            return result;
        }
        return 0;
    }

    //Số lượng khách hàng
    @GetMapping("/customer")
    public int countUserWithRoleUser() {
        int result = userRepository.countUserWithRoleUser();
        if (result > 0) {
            return result;
        }
        return 0;
    }

    //Số lượng đơn hàng đã hoàn tất
    @GetMapping("/ordersCompleted")
    public int countOrdersCompleted() {
        int result = ordersRepository.countOrdersCompleted();
        if (result > 0) {
            return result;
        }
        return 0;
    }


    //Danh thu
    @GetMapping("/revenue")
    public double getRevenue() {
        double result = ordersRepository.getRevenue();
        if (result > 0) {
            return result * 24000;
        }
        return 0.0;
    }

    public int countProduct() {
        int result = productRepository.countAllProduct();
        if (result > 0) {
            return result;
        }
        return 0;
    }

    public double getRevenueBySale() {
        double result = productRepository.getRevenueBySale();
        if (result > 0) {
            return result * 24000;
        }
        return 0.0;
    }

    public double getSpending() {
        double result = productRepository.getSpending();
        if (result > 0) {
            return result * 24000;
        }
        return 0.0;
    }

    public int countProductSold() {
        int result = ordersRepository.countProductSold();
        if (result > 0) {
            return result;
        }
        return 0;
    }
}
