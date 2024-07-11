package com.urise.webapp.storage.sql;

import com.urise.webapp.Config;
import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class SQLHelper {
    public PreparedStatement preparedStatement;

    public SQLHelper() {
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(Config.getDbConnection(), Config.getDbUser(), Config.getDbPassword());
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public void executeQuery(String queryString, FunctionExecNoReturn action) throws StorageException {
        Objects.requireNonNull(action);
        try (Connection connection = getConnection()) {
            preparedStatement = connection.prepareStatement(queryString);
            action.accept();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public <R> R executeQuery(String queryString, FunctionExecWithReturn action) throws StorageException {
        Objects.requireNonNull(action);
        try (Connection connection = getConnection()) {
            preparedStatement = connection.prepareStatement(queryString);
            return (R) action.accept();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
