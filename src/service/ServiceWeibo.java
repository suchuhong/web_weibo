package service;

import model.Comment;
import model.MySQLStorage;
import model.User;
import model.Weibo;

import java.util.ArrayList;

public class ServiceWeibo {
    public static boolean currentUserWeibo(int weiboId, int currentUserId) {
        String sql = "SELECT * FROM weibo WHERE id = ? AND userId = ?";
        ArrayList<Weibo> weibos = MySQLStorage.select(
                sql,
                resultSet -> {
                    int id = resultSet.getInt("id");
                    String content = resultSet.getString("content");
                    int userId = resultSet.getInt("userId");
                    return new Weibo(id, content, userId);
                },
                statement -> {
                    statement.setInt(1, weiboId);
                    statement.setInt(2, currentUserId);
                }
        );
        boolean result = weibos.size() > 0;
        return result;
    }

    // 1、对比当前用户 id 与评论的用户 id
    // 2、通过评论找微博 id ,对比微博用户 id 和当前用户 id
    // 符合其中之一即可
    public static boolean currentUserWeiboComment(int commentId, int currentUserId) {
        // 当前编辑用户为评论者
        String sql = "SELECT * FROM comment join weibo " +
                "on comment.weiboId = weibo.id " +
                "WHERE comment.id = ? AND (comment.userId = ? OR weibo.userId = ?)";
        ArrayList<Comment> comments = MySQLStorage.select(
                sql,
                resultSet -> {
                    int id = resultSet.getInt("id");
                    String content = resultSet.getString("content");
                    int userId = resultSet.getInt("userId");
                    int weiboId = resultSet.getInt("weiboId");
                    return new Comment(id, content, userId, weiboId);
                },
                statement -> {
                    statement.setInt(1, commentId);
                    statement.setInt(2, currentUserId);
                    statement.setInt(3, currentUserId);
                }
        );
        boolean result = comments.size() > 0;
        return result;
    }

    public static ArrayList<Weibo> currentUserWeibos(User currentUser) {
        // 选取微博
        String sql = "SELECT * FROM weibo WHERE userId = ?";
        ArrayList<Weibo> weibos = MySQLStorage.select(
                sql,
                resultSet -> {
                    int id = resultSet.getInt("id");
                    String content = resultSet.getString("content");
                    int userId = resultSet.getInt("userId");
                    return new Weibo(id, content, userId);
                },
                statement -> {
                    statement.setInt(1, currentUser.id);
                }
        );
        // 通过微博 id 获取评论
        for (int i = 0; i < weibos.size(); i++) {
            Weibo weibo = weibos.get(i);
            String commentSelect = "SELECT * FROM comment WHERE weiboId = ?";
            ArrayList<Comment> comments = MySQLStorage.select(
                    commentSelect,
                    resultSet -> {
                        int id = resultSet.getInt("id");
                        String content = resultSet.getString("content");
                        int userId = resultSet.getInt("userId");
                        return new Comment(id, content, userId, weibo.getId());
                    },
                    statement -> {
                        statement.setInt(1, weibo.getId());
                    }
            );
            weibo.setComments(comments);
        }
        
        return weibos;
    }

    public static void add(String content, User currentUser) {
        String sql = "INSERT INTO weibo (content, userId) VALUES (?, ?)";
        MySQLStorage.update(sql, statement -> {
            statement.setString(1, content);
            statement.setInt(2, currentUser.id);
        });
    }

    public static void commentAdd(String content, User currentUser, int weiboId) {
        String sql = "INSERT INTO comment (content, userId, weiboId) VALUES (?, ?, ?)";
        MySQLStorage.update(sql, statement -> {
            statement.setString(1, content);
            statement.setInt(2, currentUser.id);
            statement.setInt(3, weiboId);
        });
    }

    public static void commentDelete(int commentId) {
        String sql = "DELETE FROM comment WHERE id = ?";
        MySQLStorage.update(sql, statement -> {
            statement.setInt(1, commentId);
        });
    }

    public static void delete(int id) {
        String sql = "DELETE FROM weibo WHERE id = ?";
        MySQLStorage.update(sql, statement -> {
            statement.setInt(1, id);
        });
    }

    public static void update(int id, String content) {
        String sql = "UPDATE weibo SET content = ? WHERE id = ?";
        MySQLStorage.update(sql, statement -> {
            statement.setString(1, content);
            statement.setInt(2, id);
        });
    }

    public static void commentUpdate(int id, String content) {
        String sql = "UPDATE comment SET content = ? WHERE id = ?";
        MySQLStorage.update(sql, statement -> {
            statement.setString(1, content);
            statement.setInt(2, id);
        });
    }
}
