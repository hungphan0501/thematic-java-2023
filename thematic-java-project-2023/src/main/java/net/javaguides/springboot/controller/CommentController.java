package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.Comment;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.CommentRepository;
import net.javaguides.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class CommentController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @PostMapping("/comment/upload")
    public RedirectView uploadComment(@RequestParam("idProduct") int idProduct, @RequestParam("content") String content) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy: HH:mm:ss");
        String createAt = now.format(formatter);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username);
            if (user != null) {
                Comment comment = new Comment(user.getId(),idProduct,content,createAt,0);
                commentRepository.save(comment);
            } else {
                return new RedirectView("/login");
            }

        }else {
            return new RedirectView("/product/detail/" + idProduct);
        }
        return new RedirectView("/product/detail/" + idProduct);
    }


}
