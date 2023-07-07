package com.rajeeb.indentityservice.service;

import com.rajeeb.indentityservice.dto.AuthRequest;
import com.rajeeb.indentityservice.dto.ResetPasswordRequest;
import com.rajeeb.indentityservice.dto.ResponseMessage;
import com.rajeeb.indentityservice.entity.UserEntity;
import com.rajeeb.indentityservice.exception.UserNotFoundException;
import com.rajeeb.indentityservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    MailSenderService mailSenderService;

    @Autowired
    ResponseMessage responseMessage;

    public UserEntity getUserByName(String username){
        Optional<UserEntity> byUsername = userRepository.findByUsername(username);
        if(byUsername.isPresent()){
            return byUsername.get();
        }else throw new RuntimeException("User name not found");
    }

    public String register(UserEntity user){
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new RuntimeException("User name already exist");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "save user";
    }

    public String login( AuthRequest user) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authenticate.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        } else throw new RuntimeException("Invalid identity");
    }

    public UserEntity updateUser(UserEntity userDTO, Integer userId) throws Exception {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new UserNotFoundException("User name already exist: " + userDTO.getUsername());
        }
        if (userDTO.getUsername().length() < 3) {
            throw new UserNotFoundException("User name should be at least 3 characters ");
        }

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        return userRepository.findById(userId).map(user -> {
            user.setUsername(userDTO.getUsername());
            user.setPassword(userDTO.getPassword());
            user.setEmail(userDTO.getEmail());
            return userRepository.save(user);
        }).orElseThrow(() -> new UsernameNotFoundException("User id not found"));
    }

    public ResponseMessage updateResetPasswordToken(ResetPasswordRequest resetPasswordRequest)
            throws UserNotFoundException {
        String email = resetPasswordRequest.getEmail();
        String token = "slkdjflaskjdflsakdjflsdjflsdjf";
        String path = "http://GATEWAY-SERVICE/api/v1/user";
        try {
            String resetPasswordLink = path + "/reset-password?token=" + token;
            mailSenderService.sendEmail(email, "Forgot password link", resetPasswordLink);
            Optional<UserEntity> user = userRepository.findByEmail(email);
            if (user.isPresent()) {
                UserEntity userEntity = user.get();
                userEntity.setResetPasswordToken(token);
                userRepository.save(userEntity);
            }
        } catch (UserNotFoundException ex) {
            responseMessage.setStatus("UNSUCCESSFUL_STATUS");
            responseMessage.setText("User not found");
            return responseMessage;
        }
        responseMessage.setStatus("SUCCESSFUL_STATUS");
        responseMessage.setText("Please check your email inbox for password reset instructions.");
        return responseMessage;
    }

    public ResponseMessage getByResetPasswordToken(ResetPasswordRequest request) {
        String token = request.getToken();
        String password = request.getPassword();
        Optional<UserEntity> user = userRepository.findByResetPasswordToken(token);
        if (user.isPresent()) {
            responseMessage.setStatus("UNSUCCESSFUL_STATUS");
            responseMessage.setText("You've encountered some errors while trying to reset your password.");
            return responseMessage;
        } else {
            updatePassword(user.get(), password);
        }
        responseMessage.setStatus("SUCCESSFUL_STATUS");
        responseMessage.setText("You've successfully reset your password.");
        return responseMessage;
    }

    public void updatePassword(UserEntity user, String newPassword) {

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }
}
