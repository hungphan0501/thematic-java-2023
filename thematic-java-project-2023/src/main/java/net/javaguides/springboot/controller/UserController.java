package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.Address;
import net.javaguides.springboot.model.Cart;
import net.javaguides.springboot.model.Comment;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.AddressRepository;
import net.javaguides.springboot.repository.UserRepository;
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
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @GetMapping("/infor")
    public String getInForUser( Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username);
            if (user != null) {
                model.addAttribute("user", user);
            } else {
                return "redirect:/login";
            }
        }
     return "user/customer";
    }

    @PostMapping("/infor/update")
    public String update( User user, Model model) {
       User getUser = userRepository.getUserById(user.getId());
        getUser.setName(user.getName());
        getUser.setGender(user.getGender());
        getUser.setPhone(user.getPhone());
        getUser.setDateOfBirth(user.getDateOfBirth());
        userRepository.save(getUser);
        if(user!=null) {
            model.addAttribute("message","Cập nhật thành công!");
        }
        else {
            model.addAttribute("message","Cập nhật không thành công!");

        }
        return "user/customer";
    }

    @PostMapping("/process-form")
    public String processForm(@RequestParam("email") String email, @RequestBody ChangeUserInforDTO changeUserInforDTO, Model model) {
        String name = changeUserInforDTO.getName();
        String phone = changeUserInforDTO.getPhone();
        int day = changeUserInforDTO.getDay();
        int month = changeUserInforDTO.getMonth();
        int year = changeUserInforDTO.getYear();
        String gender = changeUserInforDTO.getGender();
        System.out.println(changeUserInforDTO);

        User getUser = userRepository.findByEmail(email);
        System.out.println(getUser);
        return "user/change-infor-user";
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
        try {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "change successfully";
    }

    //Them address
    @PostMapping("/address/add")
    public ResponseEntity<Address> addAddress(@RequestBody Address address) {
        try {
            if (address == null) {
                System.out.println("Address is null!");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            addressRepository.save(address);
            return new ResponseEntity<Address>(address, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public User getUserById (int idUser) {
        return userRepository.getUserById(idUser);
    }

}
