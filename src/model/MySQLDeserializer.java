package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface MySQLDeserializer<T> {
    T deserialize(ResultSet resultSet) throws SQLException;
}
