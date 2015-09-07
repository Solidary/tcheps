package com.tcheps.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mael-fosso on 9/2/15.
 */
public class User {

    public static final List<User> USERS = new ArrayList<User>() {{
       add(new User() {{
           setObjectId("45se12");
           setFirstName("Mael");
           setLastName("FOSSO");
           setEmail("fosso.mael.elvis@gmail.com");
           setDescription("PD4, Lycee Classique et Moderne de Maroua");
           setType("student");
       }}) ;
        add(new User() {{
            setObjectId("445812");
            setFirstName("Emery");
            setLastName("TCHEPIN");
            setEmail("emery.tchepin@gmail.com");
            setDescription("TleC, College MAZENOTH de NGaoundere");
            setType("student");
        }}) ;
        add(new User() {{
            setObjectId("gh778h");
            setFirstName("Charles");
            setLastName("TETKA");
            setEmail("charles.tetka@gmail.com");
            setDescription("TleD, Lycee LeClerc de Yaounde");
            setType("student");
        }}) ;
        add(new User() {{
            setObjectId("78lls1d");
            setFirstName("Ariel");
            setLastName("BOUNOU");
            setEmail("ariel.bounou@gmail.com");
            setDescription("TleD, College Fleming de Yaounde");
            setType("student");
        }}) ;
        add(new User() {{
            setObjectId("45se12");
            setFirstName("Mael");
            setLastName("FOSSO");
            setEmail("fosso.mael.elvis@gmail.com");
            setDescription("PD4, Lycee Classique et Moderne de Maroua");
            setType("student");
        }}) ;
        add(new User() {{
            setObjectId("445812");
            setFirstName("Emery");
            setLastName("TCHEPIN");
            setEmail("emery.tchepin@gmail.com");
            setDescription("TleC, College MAZENOTH de NGaoundere");
            setType("student");
        }}) ;
        add(new User() {{
            setObjectId("gh778h");
            setFirstName("Charles");
            setLastName("TETKA");
            setEmail("charles.tetka@gmail.com");
            setDescription("TleD, Lycee LeClerc de Yaounde");
            setType("student");
        }}) ;
        add(new User() {{
            setObjectId("78lls1d");
            setFirstName("Ariel");
            setLastName("BOUNOU");
            setEmail("ariel.bounou@gmail.com");
            setDescription("TleD, College Fleming de Yaounde");
            setType("student");
        }}) ;
    }};

    private String objectId;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String displayName;
    private String email;
    private String description;
    private String gender;
    private String password;
    private String type;

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDisplayName() {
        if (displayName == null) {
            displayName = firstName.trim() + " " + lastName.trim();
        }

        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInitials() {
        String[] sp = getDisplayName().split(" ");
        if (sp.length > 1) {
            return String.valueOf(sp[0].charAt(0)) + String.valueOf(sp[1].charAt(0));
        } else {
            return String.valueOf(sp[0].charAt(0));
        }
    }
}
