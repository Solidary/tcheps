package com.tcheps.restful.api;

import com.tcheps.models.Student;
import com.tcheps.models.Teacher;
import com.tcheps.models.User;
import com.tcheps.restful.responses.SignResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by mael-fosso on 9/5/15.
 */
public interface UserAuthenticationAPI {

    @POST("/auth/signup")
    SignResponse signUp(@Body Student user);

    @POST("/auth/signup")
    SignResponse signUp(@Body Teacher user);

    @POST("/auth/signup")
    SignResponse signUp(@Body User user);

    @FormUrlEncoded
    @POST("/auth/signin")
    SignResponse signIn(@Field("email") String email, @Field("password") String password);

}
