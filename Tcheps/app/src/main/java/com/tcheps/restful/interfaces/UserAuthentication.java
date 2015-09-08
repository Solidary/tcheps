package com.tcheps.restful.interfaces;

import com.tcheps.models.Student;
import com.tcheps.models.Teacher;
import com.tcheps.models.User;
import com.tcheps.restful.models.SignResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by mael-fosso on 9/5/15.
 */
public interface UserAuthentication {

    @POST("/auth/signup")
    void signUp(@Body Student user, Callback<SignResponse> cb);

    @POST("/auth/signup")
    void signUp(@Body Teacher user, Callback<Teacher> cb);
    /*
    @POST("/auth/signup")
    void signUp(@Body User user, Callback<User> cb);
    */
    @POST("/auth/signin")
    User signIn(String email, String password);

}
