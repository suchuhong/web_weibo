package model;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import util.Utils;

public class MySQLStorage {

    private static MysqlDataSource source;

    static {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("root");
//        dataSource.setPassword("root");
        dataSource.setServerName("127.0.0.1");
        dataSource.setPassword("zaoshuizaoqi");
//        dataSource.setServerName("127.0.0.1");
//        dataSource.setPort(3308);
        dataSource.setDatabaseName("web");


        try {
            // 本地开发不用加密链接
            dataSource.setUseSSL(false);
            // 数据库连接的编码
            dataSource.setCharacterEncoding("UTF-8");
            // 没有枚举 只能翻文档猜出时区
            dataSource.setServerTimezone("Asia/Shanghai");

            Utils.log("url: %s", dataSource.getUrl());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        source = dataSource;
    }

    // INSERT UPDATE DELETE 不返回
    public static <T> void update(String sql, MySQLSerializer<T> serializer) {
        // 可以执行 INSERT UPDATE DELETE 语句
        MysqlDataSource ds = source;
        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            serializer.serialize(statement);
            Utils.log("update %s", statement.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // 无条件查询，返回结果集
    public static <T> ArrayList<T> select(String sql, MySQLDeserializer<T> deserializer) {
        // 可以执行 SELECT 语句
        MysqlDataSource ds = source;
        ArrayList<T> results = new ArrayList<>();
        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            Utils.log("select %s", statement.toString());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                T result = deserializer.deserialize(resultSet);
                results.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return results;
    }

    // 条件查询，返回结果集
    public static <T> ArrayList<T> select(String sql, MySQLDeserializer<T> deserializer, MySQLSerializer<T> serializer) {
        MysqlDataSource ds = source;
        ArrayList<T> results = new ArrayList<>();
        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            serializer.serialize(statement);//这里不同，设置条件
            Utils.log("select %s", statement.toString());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                T result = deserializer.deserialize(resultSet);
                results.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return results;
    }

}
