package service;

import model.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.UUID;

public class ServiceAuth {

    // 换web框架之后 service/服务这一块不用动
    public static String hexFromBytes(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (int i = 0, bytesLength = bytes.length; i < bytesLength; i++) {
            byte currentByte = bytes[i];
            // 02 代表不足两位补足两位 x代表用16进制表示
            // String.format("%02x", 0) = "00"
            result.append(String.format("%02x", currentByte));
        }
        return result.toString();
    }

    public static String SaltedPasswordHash(String password, String salt) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        String salted = salt + password;
        md.update(salted.getBytes(StandardCharsets.UTF_8));
        byte[] result = md.digest();
        return hexFromBytes(result);
    }

    public static ArrayList<User> allUsers() {

        String sql = "SELECT * FROM user";
        ArrayList<User> users = MySQLStorage.select(
                sql,
                resultSet -> {
                    int id = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    UserRole role = UserRole.valueOf(resultSet.getString("role"));
                    String salt = resultSet.getString("salt");
                    return new User(id, username, password, role, salt);
                }
        );
        return users;

    }

    public static void update(String username, String newPassword) {

        String sql = "UPDATE user SET password = ? WHERE username = ?";
        MySQLStorage.update(sql, statement -> {
            statement.setString(1, newPassword);
            statement.setString(2, username);
        });

    }

    public static User guest() {
        return new User(0, "游客", "dadasds", UserRole.guest, "aaa");
    }

    public static void addSessionId(String username, String sessionId) {
        String sql = "INSERT INTO session (username, sessionId) VALUES (?, ?)";
        MySQLStorage.update(sql, statement -> {
            statement.setString(1, username);
            statement.setString(2, sessionId);
        });
    }

    public static String login(String username, String password) {

        // 找到名字
        User currentUser = userFromUsername(username);
        String salt = currentUser.salt;
        String saltedPassword = ServiceAuth.SaltedPasswordHash(password, salt);

        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        ArrayList<User> users = MySQLStorage.select(
                sql,
                resultSet -> {
                    int id = resultSet.getInt("id");
                    UserRole role = UserRole.valueOf(resultSet.getString("role"));
                    return new User(id, username, saltedPassword, role, salt);
                },
                statement -> {
                    statement.setString(1, username);
                    statement.setString(2, saltedPassword);
                }
        );
        if (users.size() > 0) {
            String sessionId = UUID.randomUUID().toString();
            addSessionId(username, sessionId);
            return sessionId;
        } else {
            return "";
        }

    }

    public static String usernameFromSessionId(String sessionId) {

        String sql = "SELECT * FROM session join user on session.username = user.username where session.sessionId = ?";
        ArrayList<User> users = MySQLStorage.select(
                sql,
                resultSet -> {
                    int id = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    UserRole role = UserRole.valueOf(resultSet.getString("role"));
                    String salt = resultSet.getString("salt");
                    return new User(id, username, password, role, salt);
                },
                statement -> {
                    statement.setString(1, sessionId);
                }
        );
        if (users.size() > 0) {
            User user = users.get(0);
            return user.getUsername();
        } else {
            return guest().getUsername();
        }

    }

    public static User userFromUsername(String username) {

        String sql = "SELECT * FROM user WHERE username = ?";
        ArrayList<User> users = MySQLStorage.select(
                sql,
                resultSet -> {
                    int id = resultSet.getInt("id");
                    String password = resultSet.getString("password");
                    UserRole role = UserRole.valueOf(resultSet.getString("role"));
                    String salt = resultSet.getString("salt");
                    return new User(id, username, password, role, salt);
                },
                statement -> {
                    statement.setString(1, username);
                }
        );
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return guest();
        }

    }

    public static User userFromId(int id) {

        String sql = "SELECT * FROM user WHERE id = ?";
        ArrayList<User> users = MySQLStorage.select(
                sql,
                resultSet -> {
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    UserRole role = UserRole.valueOf(resultSet.getString("role"));
                    String salt = resultSet.getString("salt");
                    return new User(id, username, password, role, salt);
                },
                statement -> {
                    statement.setInt(1, id);
                }
        );
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return guest();
        }

    }

    public static boolean register(String username, String password) {

        String salt = UUID.randomUUID().toString();
        String saltedPassword = ServiceAuth.SaltedPasswordHash(password, salt);

        String sql = "INSERT INTO user (username, password, role, salt) VALUES (?, ?, ?, ?)";
        MySQLStorage.update(sql, statement -> {
            statement.setString(1, username);
            statement.setString(2, saltedPassword);
            statement.setString(3, UserRole.normal.toString());
            statement.setString(4, salt);
        });

        User user = userFromUsername(username);
        boolean result = user != null;
        return result;

    }

}
