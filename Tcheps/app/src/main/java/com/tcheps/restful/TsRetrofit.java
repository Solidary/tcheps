package com.tcheps.restful;


import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by mael-fosso on 9/5/15.
 */
public class TsRetrofit {

    public final static String TS_API_URL = "http://127.0.0.0.1:3000";

    public final static Retrofit TS_RETROFIT = new Retrofit.Builder()
            .baseUrl(TS_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
