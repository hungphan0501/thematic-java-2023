package net.javaguides.springboot.controller.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class MainAdminController {

    @GetMapping("/dashboard")
    public String home() {
        return "admin/views/dist/index";
    }
}
