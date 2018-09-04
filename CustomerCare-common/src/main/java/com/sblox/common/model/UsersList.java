package com.sblox.common.model;

import java.util.ArrayList;

/*
 * Class represents needed attributes to authenticate logging in user 
 */
public class UsersList {

	private ArrayList<String> usersIds = new ArrayList<String>();
	
	

	public UsersList() {
	}

	public UsersList(ArrayList<String> usersIds) {
		super();
		this.usersIds = usersIds;
	}

	public ArrayList<String> getUsersIds() {
		return usersIds;
	}

	public void setUsersIds(ArrayList<String> usersIds) {
		this.usersIds = usersIds;
	}




	

}
