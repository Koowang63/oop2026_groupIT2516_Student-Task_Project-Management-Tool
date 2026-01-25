package edu.aitu.oop3.db.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDatabase {
    Connection getConnection() throws SQLException;
}