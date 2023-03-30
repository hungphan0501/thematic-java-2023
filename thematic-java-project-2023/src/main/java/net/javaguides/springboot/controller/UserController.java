package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.Cart;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.UserRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping
    public ResponseEntity<User> getInForUser(@RequestParam("email") String email) {
        try {
            User user = userRepository.findByEmail(email);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<User> update(@RequestParam("email") String email, @RequestBody User user) {
        try {
            User getUser = userRepository.findByEmail(email);
            if (getUser == null) {
                return ResponseEntity.notFound().build();
            }
            getUser.setName(user.getName());
            getUser.setGender(user.getGender());
            getUser.setPhone(user.getPhone());
            getUser.setDateOfBirth(user.getDateOfBirth());

            User updateUser = userRepository.save(getUser);

            return new ResponseEntity<User>(updateUser, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload2")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        // Kiểm tra xem file có tồn tại không
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
            return "uploadFailed";
        }
        try {
            int idUser = 0;
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                String username = authentication.getName();
                User user = userRepository.findByEmail(username);
                idUser = user.getId();
            }
            // Tạo đường dẫn tới thư mục chứa ảnh đại diện trên server
            String uploadDir = "src/main/resources/templates/user/image/avatar";
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

            // Tạo tên file mới
            String newFileName = idUser + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());

            // Tạo đường dẫn tới file ảnh mới trên server
            Path newFilePath = uploadPath.resolve(newFileName);

            // Nếu file đã tồn tại thì xóa file cũ trước khi lưu file mới
            if (Files.exists(newFilePath)) {
                Files.delete(newFilePath);
            }

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
    @PostMapping("/upload")
    public String uploadFile2(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        // Kiểm tra xem file có tồn tại không
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
            return "uploadFailed";
        }
        int idUser = 0;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username);
            idUser = user.getId();
        }
        // Lấy tên file gốc của ảnh đại diện
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Lấy phần mở rộng của tên file
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

        // Tạo đường dẫn tới thư mục chứa ảnh đại diện trên server
        String uploadDir = "src/main/resources/templates/user/image/avatar";
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

        // Tạo tên file mới cho ảnh đại diện
        String newFileName = idUser + fileExtension;

        // Tạo đường dẫn tới file ảnh mới trên server
        Path filePath = uploadPath.resolve(newFileName);

        // Xóa file cũ nếu tồn tại
        if (Files.exists(filePath)) {
            try {
                Files.delete(filePath);
            } catch (IOException ex) {
                ex.printStackTrace();
                redirectAttributes.addFlashAttribute("message", "Failed to delete old file " + newFileName + "!");
                return "redirect:/user/upload";
            }
        }

        try {
            // Ghi ảnh đại diện vào đường dẫn mới
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Thông báo tải ảnh đại diện thành công
            redirectAttributes.addFlashAttribute("message", "You have successfully uploaded " + newFileName + "!");
        } catch (IOException ex) {
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Failed to upload " + newFileName + "!");
        }

        return "redirect:/user/upload";
    }


}
