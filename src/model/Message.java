package model;

public class Message {
    public String author;
    public String content;

    public Message(String author, String content) {
        this.author = author;
        this.content = content;
    }

    @Override
    public String toString() {
        String s = String.format(
                "(作者 author: %s, 内容 content: %s)",
                this.author,
                this.content
        );
        return s;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
