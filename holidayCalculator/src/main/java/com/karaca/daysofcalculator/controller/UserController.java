package com.karaca.daysofcalculator.controller;

import com.karaca.daysofcalculator.Dto.UserCreateDto;
import com.karaca.daysofcalculator.Dto.UserLoginDto;
import com.karaca.daysofcalculator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserCreateDto user){
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto loginDto){
        String token = userService.loginUser(loginDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}

