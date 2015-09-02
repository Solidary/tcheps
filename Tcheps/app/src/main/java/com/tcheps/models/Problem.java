package com.tcheps.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by mael-fosso on 9/2/15.
 */
public class Problem {

    public static final List<Problem> PROBLEMS = new ArrayList<Problem>() {{
        add(new Problem() {{
            setObjectId("8895ewaf");
            setDescription("");
            setTags(Arrays.asList("maths", "discriminant", "functions"));
            setCreated(new Date());
            setAuthor(User.USERS.get(2));
        }});
        add(new Problem() {{
            setObjectId("ej209s");
            setDescription("");
            setTags(Arrays.asList("maths", "discriminant", "functions"));
            setCreated(new Date());
            setAuthor(User.USERS.get(3));
        }});
        add(new Problem() {{
            setObjectId("8d002f");
            setDescription("");
            setTags(Arrays.asList("maths", "discriminant", "functions"));
            setCreated(new Date());
            setAuthor(User.USERS.get(0));
        }});
        add(new Problem() {{
            setObjectId("faj21jf");
            setDescription("");
            setTags(Arrays.asList("maths", "discriminant", "functions"));
            setCreated(new Date());
            setAuthor(User.USERS.get(1));
        }});
        add(new Problem() {{
            setObjectId("f878dajf");
            setDescription("");
            setTags(Arrays.asList("maths", "discriminant", "functions"));
            setCreated(new Date());
            setAuthor(User.USERS.get(0));
        }});
    }};

    private String objectId;
    private String description;
    private List<String> tags;
    private String circle;
    private Date created;

    private User author;

    public Problem() {
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getCircle() {
        return circle;
    }

    public void setCircle(String circle) {
        this.circle = circle;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}