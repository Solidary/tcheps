package com.tcheps.restful.interfaces;

import com.tcheps.models.Student;
import com.tcheps.models.Teacher;
import com.tcheps.models.User;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by mael-fosso on 9/5/15.
 */
public interface UserAuthentication {

    @POST("/auth/signup")
    Student signUp(@Body Student user);

    @POST("/auth/signup")
    Teacher signUp(@Body Teacher user);

    @POST("/auth/signin")
    User signIn(String email, String password);

}
