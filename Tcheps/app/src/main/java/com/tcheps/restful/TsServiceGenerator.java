package com.tcheps.restful;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by mael-fosso on 9/5/15.
 */
public class TsServiceGenerator {

    private TsServiceGenerator() {

    }

    public static <T> T create(Class<T> serviceClass) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(TsRetrofit.TS_BASE_API_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        return restAdapter.create(serviceClass);
        // return TsRetrofit.TS_RETROFIT.create(serviceClass);
    }
}
