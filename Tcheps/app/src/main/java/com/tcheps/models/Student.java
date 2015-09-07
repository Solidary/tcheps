package com.tcheps.models;

/**
 * Created by mael-fosso on 9/5/15.
 */
public class Student extends User {

    private String level;
    private String school;

    private String _type = "student";

    public Student() {
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

    @Override
    public String getType() {
        return "student";
    }
}
