package net.javaguides.springboot.controller.Admin;

import net.javaguides.springboot.repository.OrdersRepository;
import net.javaguides.springboot.repository.ProductRepository;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    OrdersService ordersService;

    @GetMapping("/dashboard")
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("admin/views/dist/index");
        int countProduct = countProduct();
        int ordersWaiting = countOrdersWaitingCompleted();
        double revenue = getRevenue();
        double spending = getSpending();
        double revenueBySale = getRevenueBySale();
        double weekRevenue = ordersService.getWeekRevenue();

        Map<String, Object> model = new HashMap<>();
        model.put("countProduct", countProduct);
        model.put("revenue", revenue);
        model.put("spending", spending);
        model.put("revenueBySale", revenueBySale);
        model.put("ordersWaiting", ordersWaiting);
        model.put("weekRevenue", weekRevenue);

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

    public int countUser(int date) {
        return ordersService.countUserWithRoleUser(date);
    }

    public int countOrders(int date) {
        return ordersService.countOrders(date);
    }

    public double getRevenue(int date) {
        return ordersService.getRevenue(date);
    }

    public int countProductSold(int date) {
        return ordersService.countProductSold(date);
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


}
