package com.tcheps.restful.adapters;

import com.tcheps.models.User;
import com.tcheps.restful.TsServiceGenerator;
import com.tcheps.restful.api.UserAuthenticationAPI;
import com.tcheps.restful.responses.SignResponse;

import retrofit.RestAdapter;

/**
 * Created by mael-fosso on 9/11/15.
 */
public class UserAuthenticationRestAdapter {
    public final static String TAG = "UserAuthenticationRestAdapter";

    UserAuthenticationAPI mUserAuthenticationAPI;

    public UserAuthenticationRestAdapter() {
        mUserAuthenticationAPI = TsServiceGenerator.create(UserAuthenticationAPI.class);
    }

    public SignResponse signIn(String email, String password) {
        return mUserAuthenticationAPI.signIn(email, password);
    }

    public SignResponse signUp(User user) {
        return mUserAuthenticationAPI.signUp(user);
    }
}
