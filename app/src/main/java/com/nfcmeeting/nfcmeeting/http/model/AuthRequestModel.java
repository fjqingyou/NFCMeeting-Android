

package com.nfcmeeting.nfcmeeting.http.model;

import com.google.gson.annotations.SerializedName;


public class AuthRequestModel {
    private String username;
    private String password;

    public AuthRequestModel() {

    }

    public AuthRequestModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
