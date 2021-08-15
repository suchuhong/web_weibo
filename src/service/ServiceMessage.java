package service;

import model.*;

import java.util.ArrayList;

public class ServiceMessage {
    // 换web框架之后 service/服务这一块不用动
    // 用于显示所有用户的 message
    public static ArrayList<Message> allMessages() {
        // 读出 message 数据
        String sql = "SELECT * FROM message";
        ArrayList<Message> messages = MySQLStorage.select(
                sql,
                resultSet -> {
                    String author = resultSet.getString("author");
                    String content = resultSet.getString("content");
                    return new Message(author, content);
                }
        );
        return messages;
    }

    // 用于显示当前用户的 message
    public static ArrayList<Message> currentUserMessages(User currentUser) {
        // 读出 message 数据
        String sql = "SELECT * FROM message WHERE author = ?";
        ArrayList<Message> messages = MySQLStorage.select(
                sql,
                resultSet -> {
                    String author = resultSet.getString("author");
                    String content = resultSet.getString("content");
                    return new Message(author, content);
                },
                statement -> {
                    statement.setString(1, currentUser.getUsername());
                }
        );
        return messages;
    }

    public static void add(User currentUser, String content) {

        String sql = "INSERT INTO message (author, content) VALUES (?, ?)";
        MySQLStorage.update(sql, statement -> {
            statement.setString(1, currentUser.getUsername());
            statement.setString(2, content);
        });
    }

}
