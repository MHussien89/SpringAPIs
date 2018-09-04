package com.sblox.business;

import java.util.ArrayList;

import com.sblox.common.dto.UserDto;
import com.sblox.common.exception.BaseException;
//import com.sblox.dataaccess.model.PasswordResetTrial;

public interface UserBusiness {

    public long count() throws BaseException;

    public UserDto saveUser(UserDto userDto) throws BaseException;

    public String getHashedPassword(String email) throws BaseException;

    public UserDto getUserByEmail(String email) throws BaseException;

    public UserDto getUserById(long id) throws BaseException;

    public void deleteUserById(long id) throws BaseException;

    public ArrayList<UserDto> getUsersOrganizationId(long organizationId) throws BaseException;

    public ArrayList<UserDto> getPendingUsers() throws BaseException;
    
    public ArrayList<UserDto> getUpdatedUsers() throws BaseException;

    public ArrayList<UserDto> getApprovedUsers() throws BaseException;

    public ArrayList<UserDto> getRejectedUsers() throws BaseException;
    
    public ArrayList<UserDto> getNewUsers() throws BaseException;

    public void deleteUser(long id) throws BaseException;

    public ArrayList<UserDto> getSuperAdmins() throws BaseException;


}
