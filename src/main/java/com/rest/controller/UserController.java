package com.rest.controller;


import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rest.dto.UserDto;
import com.rest.service.IUserService;

@RestController
@RequestMapping("/SmsService")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@GetMapping("/signup/{mobileNo}")
	public HttpResponse<String> signUp(@PathVariable("mobileNo") String mobileNo) throws UnirestException{	
		HttpResponse<String> response = userService.mobileSignUp(mobileNo);
		return response;
	}
	
	@GetMapping("/verify/{otp}")
	public Map<String,String> verifyOtp(@PathVariable("otp") String otp){
		Map<String,String> map = new HashMap<>();
		map = userService.verifyOtp(otp);
		return map;
	}
	
	@PostMapping("/signup")
	public Map<String,String> signUp(@Valid @RequestBody UserDto userDto){
		Map<String,String> map = new HashMap<>();
		map = userService.signUp(userDto);
		return map;
	}
}
