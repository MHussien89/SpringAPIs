package com.sblox.common.model;

import java.io.Serializable;

/*
 * Class represents the returned user's data after authentication process 
 */
public class NewUserRequest implements Serializable, Cloneable, Comparable<NewUserRequest> {

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private boolean active;

	public NewUserRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compareTo(NewUserRequest o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
