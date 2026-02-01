package com.example.SBW8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.SBW8.dto.LoginDTO;
import com.example.SBW8.dto.RegisterDTO;
import com.example.SBW8.entity.Employee;
import com.example.SBW8.service.AuthService;
import com.example.SBW8.service.JwtService;

@RestController
public class AuthController {

	@Autowired
	private AuthService authService;
	@Autowired
	private JwtService jwtService;
	
	
	@PostMapping("/register")
    public Employee register(@RequestBody RegisterDTO registerDto) {
        Employee user = authService.register(registerDto);
        return user;
    }
	
	@PostMapping("/login")
	public String login(@RequestBody LoginDTO loginDto) {
		Employee user = authService.authenticate(loginDto);
		String token = jwtService.generateToken(user);
		return token;
	}
}
