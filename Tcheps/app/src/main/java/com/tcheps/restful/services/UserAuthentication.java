package com.tcheps.restful.services;

import com.tcheps.models.Student;
import com.tcheps.models.Teacher;
import com.tcheps.models.User;
import com.tcheps.restful.models.SignResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by mael-fosso on 9/5/15.
 */
public interface UserAuthentication {

    @POST("/auth/signup")
    void signUp(@Body Student user, Callback<SignResponse> cb);

    @POST("/auth/signup")
    void signUp(@Body Teacher user, Callback<SignResponse> cb);
    /*
    @POST("/auth/signup")
    void signUp(@Body User user, Callback<User> cb);
    */
    @POST("/auth/signin")
    void signIn(@Path("email")String email, @Path("password")String password, Callback<SignResponse> cb);

}
