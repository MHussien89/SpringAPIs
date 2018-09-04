package com.sblox.common.model;

/*
 * Class represents needed attributes to authenticate logging in user 
 */
public class RegisterData {

	private String firstName;
	private String lastName;
	private String userName;
	private String password;

	private String phone;
	private String email;
	private String organizationName;
	private String timeZone;

	public RegisterData() {

	}

	public RegisterData(String firstName, String lastName, String password, String phone, String email,
			String organizationName, String timeZone, String userName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.phone = phone;
		this.email = email;
		this.organizationName = organizationName;
		this.timeZone = timeZone;
		this.userName = userName;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
