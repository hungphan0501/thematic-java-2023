package net.javaguides.springboot.controller.Admin;

import net.javaguides.springboot.repository.OrdersRepository;
import net.javaguides.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/statistical")
public class HomeController {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    UserRepository userRepository ;

    //Số lượng khách hàng
    @GetMapping("/customer")
    public ResponseEntity<Integer> countUserWithRoleUser(Model model) {
        int result = userRepository.countUserWithRoleUser();
        model.addAttribute("customer",result);
        if(result>0) {
            return new ResponseEntity<Integer>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Số lượng đơn hàng đã hoàn tất
    @GetMapping("/ordersCompleted")
    public ResponseEntity<Integer> countOrdersCompleted() {
        int result = ordersRepository.countOrdersCompleted();
        if(result>0) {
            return new ResponseEntity<Integer>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //Danh thu
    @GetMapping("/revenue")
    public ResponseEntity<Double> getRevenue() {
        double result = ordersRepository.getRevenue();
        if(result>0) {
            return new ResponseEntity<Double>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
