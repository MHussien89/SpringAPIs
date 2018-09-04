package com.sblox.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.sblox.common.dto.OrganizationDto;
import com.sblox.common.dto.UserDto;
import com.sblox.common.exception.BaseException;
import com.sblox.common.model.AuthenticationResponse;
import com.sblox.common.model.UserCredentials;
import com.sblox.common.model.UserData;
import java.util.List;

public interface UserService {

    public void addUser(UserDto user, OrganizationDto organizationDto) throws BaseException;
    
    public void updateUser(UserDto user) throws BaseException;
    
    public void addSingleUser(UserDto user, long orgId) throws BaseException;
    
    public void addAdmin(long mainAdminId, UserDto user) throws BaseException;

    public AuthenticationResponse authenticateUser(UserCredentials userCredentials) throws BaseException;

    public UserDto getUserByUsername(String username) throws BaseException;
    
    public UserDto getUserById(long id) throws BaseException;

    public ArrayList<UserDto> getUsersOrganizationId(long organizationId) throws BaseException;
    
    public AuthenticationResponse getUserData(long organizationId) throws BaseException;
            
    public void deleteUsers(ArrayList<String> users ) throws IOException, BaseException;
    
    public ArrayList<UserDto> getPendingUsers() throws BaseException;
    
    public ArrayList<UserDto> getApprovedUsers() throws BaseException;
    
    public ArrayList<UserDto> getRejectedUsers() throws BaseException;
    
    public ArrayList<UserDto> getNewUsers() throws BaseException;
    
    public void updateStatus(ArrayList<UserDto> users , boolean status) throws IOException, BaseException;
        
    public void deleteUser(long id) throws BaseException;
        
    public ArrayList<UserDto> getSuperAdmins() throws BaseException;

}
