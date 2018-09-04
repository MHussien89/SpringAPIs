package com.sblox.common.model;

import com.sblox.common.dto.UserDto;
import java.io.Serializable;

public class AuthenticationResponse implements Serializable {

    private String clientId;
    private String clientSecret;
    private int accessExpiresAfter;
    private String accessToken;
    private String dataVersion;
    private UserData userData;

//    public AuthenticationResponse(UserDto userDto) {
//        dataVersion = userDto.getDataVersion();
//        this.userData = new UserData(userDto, true);
//    }
    
    public AuthenticationResponse(UserDto userDto) {
//        dataVersion = userDto.getDataVersion();
        this.userData = new UserData(userDto);
    }

    public int getAccessExpiresAfter() {
        return accessExpiresAfter;
    }

    public void setAccessExpiresAfter(int expireIn) {
        this.accessExpiresAfter = expireIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

}
