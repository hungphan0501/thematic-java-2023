package com.lastyear.semester2.thematicjava2023.controller;

import com.lastyear.semester2.thematicjava2023.model.User;
import com.lastyear.semester2.thematicjava2023.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

//    @GetMapping
//    public List<User> getAllUser() {
//        return userService.getAllUser();
//    }
    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }


    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User savedUser = userService.register(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
    @GetMapping("/register1")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "signup_form";
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        User loggedInUser = userService.login(user.getEmail(), user.getPassword());
        return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
    }
}
