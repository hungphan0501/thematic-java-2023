package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.UserOtpService;
import net.javaguides.springboot.service.UserOtpServiceImpl;
import net.javaguides.springboot.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/forgotPassword")
public class ForgotPasswordController {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserOtpServiceImpl userService;

    @GetMapping
    public ResponseEntity<?> processForgotPassword(@RequestParam("email") String email) throws MessagingException {

        // verify that email is valid and exists in the system
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("Email not found");
        }

        // generate OTP code and store it in the system
        String otpCode = generateOtpCode();
        userService.saveOtpCode(email, otpCode);
        System.out.println("otp controller:" + otpCode);

        // send OTP code to the user's email address
        sendOtpCodeByEmail(email, otpCode);

        return ResponseEntity.ok().body("OTP code sent to email");
    }


    // helper method to send OTP code by email
    private void sendOtpCodeByEmail(String email, String optCode) throws MessagingException {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("OTP Code");
        mailMessage.setText("Your OTP code is: " + optCode);

        javaMailSender.send(mailMessage);
    }
    // helper method to generate OTP code
    private String generateOtpCode() {
        int otpCode = (int) (Math.random() * 900000 + 100000);

        return String.valueOf(otpCode);
    }

    @GetMapping("/confirmOtp")
    public String confirmOtp(@RequestParam("email") String email,@RequestParam("otp") String otp) {
        boolean isValidOtpCode = userService.isValidOtpCode(email,otp);
        if(isValidOtpCode) {
            return "forgotPassword";
        }
        else
            return "error";
    }
    @GetMapping("/changNewPass")
    public ResponseEntity<?> changNewPass(@RequestParam("email") String email,@RequestParam("newPass") String newPass){
        userService.updatePassword(email,newPass);
        return ResponseEntity.ok().body("successfully");
    }

}

