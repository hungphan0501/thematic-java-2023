package net.javaguides.springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
	
	@GetMapping("/login")
	public String login() {
		return "user/login";
	}

	@GetMapping("/forgotPasswordPage")
	public String forgotPassword() {
		return "user/forgotPassword";
	}

}
