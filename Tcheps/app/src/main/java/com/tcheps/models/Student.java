package com.tcheps.models;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mael-fosso on 9/5/15.
 */
public class Student extends User {

    @SerializedName("level")
    private String level;
    @SerializedName("school")
    private String school;
    @SerializedName("_type")
    private String type = "student";

    public Student() {
        super();
    }

    private Student(Parcel parcel) {
        super(parcel);
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    @Override
    public String getDescription() {
        return level + ", " + school;
    }

    public String getType() {
        return "student";
    }


}
