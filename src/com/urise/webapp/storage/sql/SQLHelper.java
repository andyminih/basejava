package com.urise.webapp.storage.sql;

import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class SQLHelper {
    private final Connection connection;

    public SQLHelper(String dbUrl, String dbUser, String dbPassword) {
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void executeQuery(FunctionExecNoReturn action) throws StorageException {
        Objects.requireNonNull(action);
        try {
//            preparedStatement = connection.prepareStatement(queryString);
            action.accept();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public <R> R executeQuery(FunctionExecWithReturn action) throws StorageException {
        Objects.requireNonNull(action);
        try {
            return (R) action.accept();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
