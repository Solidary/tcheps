package com.tcheps.models;

/**
 * Created by mael-fosso on 9/5/15.
 */
public class Teacher extends User {

    private String placeType;
    private String placeName;
    private String subject;

    private String _type = "teacher";

    public Teacher() {
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String getDescription() {
        return subject + ", " + placeName;
    }
}
