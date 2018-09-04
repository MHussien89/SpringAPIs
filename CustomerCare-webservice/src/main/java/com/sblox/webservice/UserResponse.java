package com.sblox.webservice;

import java.util.ArrayList;
import java.util.List;

import com.sblox.common.dto.UserDto;

public class UserResponse {

	List<UserDto> users;

	public UserResponse() {
		super();
	}

	public UserResponse(List<UserDto> users) {
		super();
		this.users = users;
	}

	public List<UserDto> getUsers() {
		return users;
	}

	public void setUsers(List<UserDto> users) {
		this.users = users;
	}

}
