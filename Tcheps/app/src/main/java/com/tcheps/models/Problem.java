package com.tcheps.models;

import com.google.gson.annotations.SerializedName;
import com.tcheps.AuthPreferences;
import com.tcheps.TsApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by mael-fosso on 9/2/15.
 */
public class Problem {

    /*public static final List<Problem> PROBLEMS = new ArrayList<Problem>() {{
        add(new Problem() {{
            setObjectId("8895ewaf");
            setDescription("");
            setTags(Arrays.asList("maths", "discriminant", "functions"));
            setCreated(new Date());
            setAuthor(User.USERS.get(2));
            setComments(Comment.COMMENTS);
        }});
        add(new Problem() {{
            setObjectId("ej209s");
            setDescription("");
            setTags(Arrays.asList("maths", "discriminant", "functions"));
            setCreated(new Date());
            setAuthor(User.USERS.get(3));
            setComments(Comment.COMMENTS);
        }});
        add(new Problem() {{
            setObjectId("8d002f");
            setDescription("");
            setTags(Arrays.asList("maths", "discriminant", "functions"));
            setCreated(new Date());
            setAuthor(User.USERS.get(0));
            setComments(Comment.COMMENTS);
        }});
        add(new Problem() {{
            setObjectId("faj21jf");
            setDescription("");
            setTags(Arrays.asList("maths", "discriminant", "functions"));
            setCreated(new Date());
            setAuthor(User.USERS.get(1));
            setComments(Comment.COMMENTS);
        }});
        add(new Problem() {{
            setObjectId("f878dajf");
            setDescription("");
            setTags(Arrays.asList("maths", "discriminant", "functions"));
            setCreated(new Date());
            setAuthor(User.USERS.get(0));
            setComments(Comment.COMMENTS);
        }});
    }};*/

    @SerializedName("_id")
    private String objectId;

    @SerializedName("description")
    private String description;

    @SerializedName("subject")
    private String subject;

    @SerializedName("created")
    private Date created;


    List<Comment> comments;
    @SerializedName("likes")
    ArrayList<Like> likes = new ArrayList<Like>();
    @SerializedName("followers")
    ArrayList<Follower> followers = new ArrayList<Follower>();

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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


    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public ArrayList<Follower> getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<Follower> followers) {
        this.followers = followers;
    }

    public ArrayList<Like> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<Like> likes) {
        this.likes = likes;
    }


    public boolean isUserHasLiked(User user) {
        for (Like like: likes) {
            if (like.getAuthor().getObjectId().equals(user.getObjectId())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "objectId='" + objectId + '\'' +
                ", description='" + description + '\'' +
                ", subject='" + subject + '\'' +
                ", created=" + created +
                '}';
    }

}
