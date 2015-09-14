package com.tcheps;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.tcheps.models.User;

/**
 * Created by mael-fosso on 9/12/15.
 */
public class AuthPreferences {

    public static final String KEY_USER =
            "com.tcheps.AuthPreferences.key_user";
    public static final String KEY_TOKEN =
            "com.tcheps.AuthPreferences.key_token";


    private SharedPreferences tsPreferences;

    public AuthPreferences(Context context) {
        tsPreferences = context.getSharedPreferences("com.tcheps.auth", Context.MODE_PRIVATE);
    }

    public void setUser(User user) {
        SharedPreferences.Editor editor = tsPreferences.edit();
        editor.putString(KEY_USER, new Gson().toJson(user));
        editor.commit();
    }

    public void setToken(String token) {
        SharedPreferences.Editor editor = tsPreferences.edit();
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }

    public User getUser() {
        return new Gson().fromJson(tsPreferences.getString(KEY_USER, null), User.class);
    }

    public String getToken() {
        return tsPreferences.getString(KEY_TOKEN, null);
    }
}
