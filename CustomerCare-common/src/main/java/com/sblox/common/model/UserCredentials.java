package com.sblox.common.model;

/*
 * Class represents needed attributes to authenticate logging in user 
 */
public class UserCredentials {

    private String username;
    private String password;

    public UserCredentials() {

    }

    public UserCredentials(String username, String password) {
	this.username = username;
	this.password = password;
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

}
