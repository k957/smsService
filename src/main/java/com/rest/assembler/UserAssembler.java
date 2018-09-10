package com.rest.assembler;

import org.springframework.stereotype.Component;

import com.rest.dto.UserDto;
import com.rest.model.User;

@Component
public class UserAssembler {

	public User createUserEntity(UserDto userDto) {
		User user = new User();
		user.setUsername(userDto.getUsername());
		user.setMobileNo(userDto.getMobileNo());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setAddress(userDto.getAddress());
		user.setPassword(userDto.getPassword());
		return user;
	}
}
