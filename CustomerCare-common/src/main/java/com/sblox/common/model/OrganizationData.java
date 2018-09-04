package com.sblox.common.model;

import java.io.Serializable;

/*
 * Class represents the returned user's data after authentication process 
 */
public class OrganizationData implements Serializable, Cloneable, Comparable<OrganizationData> {

	private long id;
	private String name;
	private int numberOfActiveUsers;
	private boolean active;

	public OrganizationData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrganizationData(long id, String name, int numberOfActiveUsers, boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.numberOfActiveUsers = numberOfActiveUsers;
		this.active = active;
	}

	@Override
	public int compareTo(OrganizationData o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getId() {
		return id;
	}

	public void setd(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumberOfActiveUsers() {
		return numberOfActiveUsers;
	}

	public void setNumberOfActiveUsers(int numberOfActiveUsers) {
		this.numberOfActiveUsers = numberOfActiveUsers;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
