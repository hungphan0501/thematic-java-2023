package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.*;
import net.javaguides.springboot.repository.*;
import net.javaguides.springboot.web.dto.ChangeUserInforDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    CartRepository cartRepository;
    @GetMapping("/infor")
    public String getInForUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username);
            if (user != null) {
                List<Orders> ordersList = ordersRepository.getAllByIdUser(user.getId());
                List<Address> addresses = addressRepository.getAddressByIdUser(user.getId());
                double totalPrice = 0;
                List<Cart> carts = cartRepository.getAllProductInCartOfCustomer(user.getId());
                if(carts!= null){
                    for (Cart cart : carts) {
                        totalPrice += cart.getTotalPrice();
                    }
                }
                model.addAttribute("totalPrice", totalPrice);
                model.addAttribute("carts", carts);
                model.addAttribute("addresses", addresses);
                model.addAttribute("user", user);
                model.addAttribute("orders", ordersList);

            } else {
                return "redirect:/login";
            }
        }
        return "user/customer";
    }

    @GetMapping("/history/{idOrder}")
    public String getHistory(@PathVariable("idOrder") int idOrder ,Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username);
            if (user != null) {
                List<OrderDetail> orderDetails = orderDetailRepository.getAllByIdOrder(idOrder);
                double totalPrice = 0;
                List<Cart> carts = cartRepository.getAllProductInCartOfCustomer(user.getId());
                if(carts!= null){
                    for (Cart cart : carts) {
                        totalPrice += cart.getTotalPrice();
                    }
                }
                model.addAttribute("totalPrice", totalPrice);
                model.addAttribute("carts", carts);
                model.addAttribute("orderDetails", orderDetails);
                model.addAttribute("user", user);
            } else {
                return "redirect:/login";
            }
        }
        model.addAttribute("orderId", idOrder);

        return "user/orders";
    }

    @PostMapping("/infor/update")
    public String update(User user, Model model) {
        User getUser = userRepository.getUserById(user.getId());
        getUser.setName(user.getName());
        getUser.setGender(user.getGender());
        getUser.setPhone(user.getPhone());
        getUser.setDateOfBirth(user.getDateOfBirth());
        userRepository.save(getUser);
        if (user != null) {
            model.addAttribute("message", "Cập nhật thông tin thành công!");
        } else {
            model.addAttribute("message", "Cập nhật thông tin không thành công!");

        }
        return "redirect:/user/infor";
    }

    @PostMapping("/infor/update-address/{idAddress}")
    public String updateAddress(Address address, @PathVariable("idAddress") int idAddress, Model model) {
        RedirectView redirectView = new RedirectView();
        Address addressById = addressRepository.getAddressById(idAddress);
        System.out.println(addressById);
        addressById.setCity(address.getCity());
        addressById.setDistrict(address.getDistrict());
        addressById.setWard(address.getWard());
        addressById.setSpecificAddress(address.getSpecificAddress());
        addressById.setPhone(address.getPhone());
        addressById.setUserName(address.getUserName());
        addressById.setIsDefault(address.getIsDefault());
        addressRepository.save(addressById);


        return "redirect:/user/infor";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        // Kiểm tra xem file có tồn tại không
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
            return "uploadFailed";
        }
        if (file.getSize() > 1048576) {
            redirectAttributes.addFlashAttribute("message", "File size exceeds maximum permitted size of 1MB.");
            return "file exceeded size";
        }
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        System.out.println("file format: " + extension);
        String[] fileFormats = {"jpg", "png", "svg", "jpeg", "gif"};
//        for (String format : fileFormats) {
//            if (!extension.equals(format)) {
//                return "file invalid format";
//            }
//        }

        try {
            //lấy id user hiện tại
            int idUser = 0;
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                String username = authentication.getName();
                User user = userRepository.findByEmail(username);
                idUser = user.getId();
            }
            // Tạo đường dẫn tới thư mục chứa ảnh đại diện trên server
            String uploadDir = "src/main/resources/templates/user/image/avatar";
            File dir = new File(uploadDir);

            // Kiểm tra xem thư mục lưu trữ avatar có tồn tại hay không, nếu không thì tạo mới
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Lấy danh sách các tệp tin trong thư mục lưu trữ avatar
            File[] files = dir.listFiles();

            //kiểm tra các file trong thư mục nếu tồn tại file có tên file là idUser hiện tại thì sẽ delete
            for (File f : files) {
                if (f.getName().startsWith(String.valueOf(idUser + "."))) {
                    f.delete();
                }
            }
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

            // Tạo tên file mới
            String newFileName = idUser + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());

            // Tạo đường dẫn tới file ảnh mới trên server
            Path newFilePath = uploadPath.resolve(newFileName);


            // Ghi ảnh đại diện vào đường dẫn mới
            Files.copy(file.getInputStream(), newFilePath, StandardCopyOption.REPLACE_EXISTING);

            // Thông báo tải ảnh đại diện thành công
            redirectAttributes.addFlashAttribute("message", "You have successfully uploaded " + newFilePath + "!");
        } catch (IOException ex) {
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Failed to upload " + file.getOriginalFilename() + "!");
        }
        return "uploadSuccess";
    }


    //thay đổi địa chỉ mặc định
    @PostMapping("/address/changeDefault/{idAddress}")
    public String changeAddressDefault(@PathVariable("idAddress") int idAddress) {
        int idUser = 0;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username);
            idUser = user.getId();
        }
        List<Address> list = addressRepository.getAllByIdUser(idUser);
        System.out.println(list.toString());
        Address address = addressRepository.getAddressById(idAddress);
        System.out.println("address:  " + address);
        address.setIsDefault(0);
        addressRepository.save(address);
        for (Address a : list) {
            if (!(address.getId() == a.getId())) {
                a.setIsDefault(1);
                addressRepository.save(a);
            }
        }

        User user = userRepository.getUserById(idUser);
        user.setIdAddress(idAddress);
        userRepository.save(user);

        return "change successfully";
    }

    //Them address
    @PostMapping("/infor/address/add")
    public RedirectView addAddress(Address address) {
        RedirectView redirectView = new RedirectView();
        int idUser = 0;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username);
            idUser = user.getId();
        }
        System.out.println(address);
        if (address == null) {
            System.out.println("Address is null!");

        }
        address.setIdUser(idUser);
        addressRepository.save(address);
        redirectView.setUrl("/user/infor");
        return redirectView;
    }


    @PostMapping("/infor/order-history/details")
    public String getOrderDetails(@RequestParam("orderId") int orderId, Model model) {
        int idUser = 0;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username);
            idUser = user.getId();
        }

        List<OrderDetail> orderDetails = orderDetailRepository.getAllByIdOrder(orderId);
        User user = userRepository.getUserById(idUser);
        List<Orders> ordersList = ordersRepository.getAllByIdUser(idUser);
        int total = 0;
        for (Orders orders : ordersList) {
            total += orders.getTotalPrice();
        }
        model.addAttribute("total", total);
        model.addAttribute("user", user);
        model.addAttribute("orderId", orderId);
        model.addAttribute("orderDetails", orderDetails);
        return "user/customer";
    }

    public Product getProductByIdProductDetail(int idProductDetail) {
        ProductDetail productDetail = orderDetailRepository.getProductDetailById(idProductDetail);
        Product product = orderDetailRepository.getProductByIdProductDetail(productDetail.getId());
        return product;
    }

    public User getUserById(int idUser) {
        return userRepository.getUserById(idUser);
    }

    public String nameByEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username);
            return user.getName();
        } else {
            return "";
        }
    }

    public String getAvatar() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username);
            return user.getAvatar();
        } else {
            return "";
        }
    }
}
