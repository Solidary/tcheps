package com.tcheps.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mael-fosso on 9/2/15.
 */
public class User implements Parcelable {

    @SerializedName("_id")
    private String objectId;

    @SerializedName("name")
    private String name;

    @SerializedName("photo")
    private String photo;

    @SerializedName("email")
    private String email;

    @SerializedName("level")
    private String level;

    @SerializedName("school")
    private String school;

    @SerializedName("gender")
    private String gender;

    @SerializedName("password")
    private String password;

    public User() {
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "objectId='" + objectId + '\'' +
                ", name='" + name + '\'' +
                ", photo=" + photo +
                ", email='" + email + '\'' +
                ", level='" + level + '\'' +
                ", school='" + school + '\'' +
                ", gender='" + gender + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getDescription() {
        return level + ", " + school;
    }

    public String getInitials() {
        String[] sp = getName().split(" ");
        if (sp.length > 1) {
            return String.valueOf(sp[0].charAt(0)) + String.valueOf(sp[1].charAt(0));
        } else {
            return String.valueOf(sp[0].charAt(0));
        }
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(objectId);
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(gender);
        parcel.writeString(photo);

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        // parcel.writeString(df.format(birthDate));
    }

    protected User(Parcel parcel) {
        objectId = parcel.readString();
        name = parcel.readString();
        email = parcel.readString();
        gender = parcel.readString();
        photo = parcel.readString();

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        /*try {
            birthDate = df.parse(parcel.readString());
        } catch (ParseException pex) {
            // birthDate = df.parse("01/01/1900");
        }*/
    }
}
