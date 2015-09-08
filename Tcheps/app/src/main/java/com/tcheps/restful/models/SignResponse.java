package com.tcheps.restful.models;

import com.google.gson.annotations.SerializedName;
import com.tcheps.models.User;

/**
 * Created by mael-fosso on 9/8/15.
 */
public class SignResponse {

    @SerializedName("user")
    private User user;
    @SerializedName("token")
    private String token;

    public SignResponse() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
