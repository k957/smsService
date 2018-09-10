package com.rest.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rest.assembler.UserAssembler;
import com.rest.dto.UserDto;
import com.rest.model.User;
import com.rest.repository.IUserRepository;

import redis.clients.jedis.Jedis;



@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	private UserAssembler userAssembler;
	
	@Autowired
	private IUserRepository userRepository;
	
	Jedis jedis = new Jedis("localhost");
	
	
	@Override
	public HttpResponse<String> mobileSignUp(String mobileNo) throws UnirestException {
		List<User> user = userRepository.findByMobileNo(mobileNo);
		if(user.isEmpty()) {
			String otp = otp();
			String message = "Hi%20"+mobileNo+"%20Welcome!%20Your%20Otp%20is:%20"+otp;
			HttpResponse<String> response = Unirest.get("http://api.msg91.com/api/sendhttp.php?country=91&sender=MSGIND&route=4&mobiles="+mobileNo+"&authkey=235927Ayxa0pYf5b90bba9&encrypt=&message="+message)
					  .asString();
			jedis.set(otp, mobileNo);
			jedis.expire(otp, 900);
			return response;
		}
		else {
			return null;
		}
	}
	
	@Override
	public Map<String, String> verifyOtp(String otp) {
		Map<String,String> map = new HashMap<>();
		if(jedis.get(otp)!=null) {
			String mobileNo = jedis.get(otp);
			map.put("mobile number", mobileNo);
			map.put("message", "User verified");
			jedis.del(otp);
		}
		return map;
	}
	
	@Override
	public Map<String, String> signUp(UserDto userDto) {
		User user = userAssembler.createUserEntity(userDto);
		userRepository.save(user);
		Map<String,String> map = new HashMap<>();
		map.put("message", "user registered");
		return map;
	}

	public String otp()
    {
       
        int len=6;
        // Using numeric values
        String numbers = "0123456789";
 
        // Using random method
        Random rndmMethod = new Random();
 
        char[] otp = new char[len];
        otp[0]='S';
        for (int i = 1; i <= len-1; i++)
        {
            // Use of charAt() method : to get character value
            // Use of nextInt() as it is scanning the value as int
            otp[i] = numbers.charAt(rndmMethod.nextInt(numbers.length()));
        }
        String o = new String(otp);
        return o;
    }
}
