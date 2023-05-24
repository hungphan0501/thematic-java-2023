package net.javaguides.springboot.controller.Admin;

import net.javaguides.springboot.model.*;
import net.javaguides.springboot.repository.OrderDetailRepository;
import net.javaguides.springboot.repository.OrdersRepository;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.OrdersService;
import net.javaguides.springboot.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    OrdersService ordersService;


    @GetMapping("/users-manage")
    public String getUserMange(Model model) {
        List<User> getAllUser = userRepository.getAllUser();
        model.addAttribute("users", getAllUser);

        return "admin/views/dist/customers";
    }

    @RequestMapping(value = "/users-manage/remove/{idUser}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String removeProduct(@PathVariable("idUser") int idUser, Model model) {
        userService.removeUser(idUser);
        model.addAttribute("message", "Xóa khách hàng " + idUser +" thành công!");
        List<User> getAllUser = userRepository.getAllUser();
        model.addAttribute("users", getAllUser);
        return "admin/views/dist/products-list";
    }

    @GetMapping("/users-manage/user/detail/{idUser}")
    public String getUserDetailPage(@PathVariable("idUser") int idUser, Model model) {
        User user = userRepository.getUserById(idUser);
        List<Orders> ordersList = ordersRepository.getAllByIdUser(idUser);
        int total=0;
        for(Orders orders : ordersList){
            total+=orders.getTotalPrice();
        }

        model.addAttribute("user",user);
        model.addAttribute("orders", ordersList);
        model.addAttribute("total", total);
        return "admin/views/dist/customer-details";
    }

    @GetMapping("/users-manage/{idUser}/order/details/{orderId}")
    public String getOrderDetails(@PathVariable("idUser") int idUser,@PathVariable("orderId") int orderId , Model model) {
        List<OrderDetail> orderDetails = orderDetailRepository.getAllByIdOrder(orderId);
        User user = userRepository.getUserById(idUser);
        List<Orders> ordersList = ordersRepository.getAllByIdUser(idUser);
        int total=0;
        for(Orders orders : ordersList){
            total+=orders.getTotalPrice();
        }
        model.addAttribute("total", total);
        model.addAttribute("user",user);
        model.addAttribute("orderId",orderId);
        model.addAttribute("orderDetails", orderDetails);
        return "admin/views/dist/customer-order-detail-manage";
    }

    public Product getProductByIdProductDetail (int idProductDetail) {
        ProductDetail productDetail = orderDetailRepository.getProductDetailById(idProductDetail);
        Product product = orderDetailRepository.getProductByIdProductDetail(productDetail.getId());
        return product;
    }

    @RequestMapping(value = "/users-manage/{idUser}/order/remove/{orderId}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String removeOrder(@PathVariable("idUser") int idUser, @PathVariable("orderId") int orderId, RedirectAttributes redirectAttributes) {
        ordersService.removeOrder(orderId);
        redirectAttributes.addFlashAttribute("message", "Xóa hóa đơn #" +orderId+" thành công!");

        return "redirect:/users-manage/user/detail/" + idUser;
    }

}
