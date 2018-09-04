package com.sblox.common.dto;

import java.io.Serializable;
import java.security.Principal;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDto implements Principal , Serializable {

    private Long id;

    private boolean active;

    private String dataVersion;

    private String firstName;

    private String lastName;
    
    private String userName;

    private String phone;

    private String email;

    private String password;
    
    private String hashedPassword;

    private String primaryUser;

    private String timeZone;

    private RoleDto role;

    private OrganizationDto organization;

    private boolean checkbox;
    
    private boolean updated;
    

    public Long getId() {
        return id;
    }

    private String firstLogin;

    private String securityToken;

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    
    public OrganizationDto getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationDto organization) {
        this.organization = organization;
    }

    public RoleDto getRole() {
        return role;
    }

    public void setRole(RoleDto role) {
        this.role = role;
    }

    public String getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(String firstLogin) {
        this.firstLogin = firstLogin;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public String getPrimaryUser() {
        return primaryUser;
    }

    public void setPrimaryUser(String primaryUser) {
        this.primaryUser = primaryUser;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTimeZone() {
        return timeZone;
    }

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
