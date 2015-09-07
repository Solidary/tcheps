package com.tcheps.restful;

/**
 * Created by mael-fosso on 9/5/15.
 */
public class TsServiceGenerator {

    private TsServiceGenerator() {

    }

    public static <T> T create(Class<T> serviceClass) {

        return TsRetrofit.TS_RETROFIT.create(serviceClass);
    }
}
