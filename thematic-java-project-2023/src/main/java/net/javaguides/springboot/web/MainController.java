package net.javaguides.springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/login")
	public String login() {
		return "user/login";
	}
	
	@GetMapping("/")
	public String home() {
		return "user/index";
	}
	@GetMapping("/forgotPasswordPage")
	public String forgotPassword() {
		return "user/forgotPassword";
	}
}
