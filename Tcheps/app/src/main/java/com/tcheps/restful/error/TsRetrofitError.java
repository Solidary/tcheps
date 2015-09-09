package com.tcheps.restful.error;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by mael-fosso on 9/8/15.
 */
public class TsRetrofitError {
    @SerializedName("code")
    public int code;
    @SerializedName("error")
    public String error;

    public TsRetrofitError() {
    }
}
