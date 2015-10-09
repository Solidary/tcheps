package com.tcheps.restful;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by mael-fosso on 9/5/15.
 */
public class TsServiceGenerator {

    private TsServiceGenerator() {

    }

    public static <T> T create(Class<T> serviceClass) {

        return create(serviceClass, null);
    }

    public static <S> S create(Class<S> serviceClass, final String token) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(TsRetrofit.TS_BASE_API_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(new OkHttpClient()))
                .setConverter(new GsonConverter(gson));

        if (token != null) {
            builder.setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("Accept", "application/json");
                    request.addHeader("Authorization", "Bearer " + token);
                }
            });
        }

        RestAdapter adapter = builder.build();
        return adapter.create(serviceClass);
    }
}
