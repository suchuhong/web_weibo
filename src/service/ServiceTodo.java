package service;

import model.*;

import java.util.ArrayList;

public class ServiceTodo {

    public static boolean currentUserTodo(int todoId, int currentUserId) {
        String sql = "SELECT * FROM todo WHERE id = ? AND userId = ?";
        ArrayList<Todo> todos = MySQLStorage.select(
                sql,
                resultSet -> {
                    int id = resultSet.getInt("id");
                    String content = resultSet.getString("content");
                    int userId = resultSet.getInt("userId");
                    return new Todo(id, content, userId);
                },
                statement -> {
                    statement.setInt(1, todoId);
                    statement.setInt(2, currentUserId);
                }
        );
        boolean result = todos.size() > 0;
        return result;
    }

    // 换web框架之后 service/服务这一块不用动
    public static ArrayList<Todo> currentUserTodos(User currentUser) {
        String sql = "SELECT * FROM todo WHERE userId = ?";
        ArrayList<Todo> todos = MySQLStorage.select(
                sql,
                resultSet -> {
                    int id = resultSet.getInt("id");
                    String content = resultSet.getString("content");
                    int userId = resultSet.getInt("userId");
                    return new Todo(id, content, userId);
                },
                statement -> {
                    statement.setInt(1, currentUser.id);
                }
        );
        return todos;
    }

    public static void add(String content, User currentUser) {
        String sql = "INSERT INTO todo (content, userId) VALUES (?, ?)";
        MySQLStorage.update(sql, statement -> {
            statement.setString(1, content);
            statement.setInt(2, currentUser.id);
        });
    }

    public static void delete(int id) {
        String sql = "DELETE FROM todo WHERE id = ?";
        MySQLStorage.update(sql, statement -> {
            statement.setInt(1, id);
        });
    }

    public static void update(int id, String content) {
        String sql = "UPDATE todo SET content = ? WHERE id = ?";
        MySQLStorage.update(sql, statement -> {
            statement.setString(1, content);
            statement.setInt(2, id);
        });
    }
}
