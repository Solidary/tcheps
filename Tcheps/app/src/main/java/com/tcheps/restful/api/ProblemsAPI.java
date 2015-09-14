package com.tcheps.restful.api;

import com.tcheps.models.Problem;
import com.tcheps.models.User;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by mael-fosso on 9/9/15.
 */
public interface ProblemsAPI {

    @POST("/problems")
    void create(@Body Problem problem, Callback<Problem> cb);

    @GET("/problems/{id}")
    void read(@Path("id") String id, Callback<Problem> cb);

    @GET("/problems")
    List<Problem> list();

    @POST("/problems/{id}/likes")
    void like(@Path("id") String id, Callback<Boolean> cb);

    @GET("/problems/{id}/likes")
    List<User> likers(@Path("id") String id);
}
