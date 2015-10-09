package com.tcheps.restful;


import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by mael-fosso on 9/5/15.
 */
public class TsRetrofit {

    public final static String TS_BASE_API_URL = "http://192.168.57.1:3000"; //"http://192.168.56.1:3000";


    /*public final static Retrofit TS_RETROFIT = new Retrofit.Builder()
            .baseUrl(TS_BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();*/

    public final static RestAdapter TS_RETROFIT = new RestAdapter.Builder()
            .setEndpoint(TS_BASE_API_URL)
            .setClient(new OkClient(new OkHttpClient()))
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setConverter(new GsonConverter(new GsonBuilder().create()))
            .build();
}
