package com.rest.service;

import java.util.Map;



import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rest.dto.UserDto;

public interface IUserService {
	
	HttpResponse<String> mobileSignUp(String mobilNo) throws UnirestException;
	Map<String,String> verifyOtp(String otp);
	Map<String,String> signUp(UserDto userDto);
}
