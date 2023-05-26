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
	
//	@GetMapping("/")
//	public String home() {
//		return "user/home";
//	}
	@GetMapping("/category")
	public String category(){ return "user/category";}
	@GetMapping("/userinfor")
	public String userinfor(){ return "user/customer";}

	@GetMapping("/forgotPasswordPage")
	public String forgotPassword() {
		return "user/forgotPassword";
	}

	@GetMapping("/avatar")
	public String showUploadForm() {
		return "user/avatar";
	}
}
