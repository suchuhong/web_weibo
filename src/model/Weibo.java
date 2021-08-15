package model;

import java.util.ArrayList;

public class Weibo {
    private int id;
    private String content;
    private int userId;
    private ArrayList<Comment> comments;

    public Weibo(int id, String content, int userId) {
        this.id = id;
        this.content = content;
        this.userId = userId;
    }

    @Override
    public String toString() {
        String s = String.format(
                "(id: %s, 内容: %s, user_id: %s)",
                this.getId(),
                this.getContent(),
                this.getUserId()
        );
        return s;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
