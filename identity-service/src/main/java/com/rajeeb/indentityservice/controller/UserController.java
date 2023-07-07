package com.rajeeb.indentityservice.controller;

import com.rajeeb.indentityservice.dto.AuthRequest;
import com.rajeeb.indentityservice.dto.ResetPasswordRequest;
import com.rajeeb.indentityservice.dto.ResponseMessage;
import com.rajeeb.indentityservice.entity.UserEntity;
import com.rajeeb.indentityservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private AuthService authService;

    @GetMapping("/getUser/{username}")
    public UserEntity getUserByUsername(@PathVariable String username){
       return authService.getUserByName(username);
    }

    @GetMapping("/get")
    public String getUser(){
       return "Massage for get method";
    }

    @PutMapping("/{id}")
    public UserEntity updateUser(@RequestBody UserEntity user,@PathVariable Integer id) throws Exception {
        return authService.updateUser(user,id);
    }

    @PostMapping("/signup")
    public void register(@RequestBody UserEntity userEntity) {
        authService.register(userEntity);
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest user) {
       return authService.login(user);
    }

    @PostMapping("/forgot_password")
    public ResponseMessage processForgotPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        return authService.updateResetPasswordToken(resetPasswordRequest);
    }

    @PostMapping("/reset_password")
    public ResponseMessage processResetPassword(@RequestBody ResetPasswordRequest request) {
        return authService.getByResetPasswordToken( request );
    }
}

