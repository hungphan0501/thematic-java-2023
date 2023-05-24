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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.Comparator;
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
    @PostMapping("/users-manage")
    public String getListUserManageForm(@RequestParam("type") String type, @RequestParam("value") String value, @RequestParam("sort") String sort, Model model) {
        String error = "";
        List<User> users;
        try {
            switch (type) {
                case "id":
                    if (isNumber(value) == true) {
                        int id = Integer.parseInt(value);
                        users = userRepository.findAllById(Collections.singleton(id));
                        if (users.isEmpty()) {
                            error = "ID khách hàng không đúng!";
                            model.addAttribute("error", error);
                        } else {
                            model.addAttribute("users", users);
                        }
                    } else {
                        error = "ID không phải là số!";
                        model.addAttribute("error", error);

                    }
                    break;
                case "email":
                    users =userRepository.findAllByEmail(value);
                    if (users.isEmpty()) {
                        error = "Email khách hàng không đúng!";
                        model.addAttribute("error", error);
                    } else {
                        model.addAttribute("users", users);
                    }
                    break;

                case "name":
                    if (value.equals("")) {
                        error = "Tên khách hàng được để trống!";
                        model.addAttribute("error", error);
                    } else {
                        users = userRepository.getAllByName(value);
                        sortUser(sort, users, "name");
                        if (users.isEmpty()) {
                            error = "Tên khách hàng không tồn tại!";
                            model.addAttribute("error", error);
                        } else {
                            model.addAttribute("users", users);
                        }
                    }

                    break;


                default:
                    break;
            }
            return "admin/views/dist/customers";

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
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
    public boolean isNumber(String str) {
        if (str == null || str.isEmpty() || str.equals("")) {
            return false;
        }

        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public List<User> sortUser(String sort, List<User> users, String type) {
        int num = Integer.parseInt(sort);
        if (num == 0) {
            switch (type) {
                case "name":
                    Collections.sort(users, Comparator.comparing(User::getName));
                    break;
                default:
                    break;
            }

        } else if (num == 1) {
            switch (type) {
                case "name":
                    Collections.sort(users, Comparator.comparing(User::getName).reversed());
                    break;
                default:
                    break;
            }
        }
        return users;
    }
}
