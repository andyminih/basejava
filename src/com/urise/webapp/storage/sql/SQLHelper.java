package com.urise.webapp.storage.sql;

import com.urise.webapp.exception.ExistsStorageException;
import com.urise.webapp.exception.StorageException;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class SQLHelper {
    private final static String PSQL_UNIQUE_VIOLATION_ERROR = "23505";
    private final Connection connection;

    public final ConnectionFactory connectionFactory;

    public SQLHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        try {
            connection = connectionFactory.getConnection();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public void executeQuery(String queryString, String uuid, FunctionExecNoReturn action) throws StorageException {
        Objects.requireNonNull(action);
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            action.accept(preparedStatement);
        } catch (SQLException e) {
            if (e instanceof PSQLException
                    && (e.getSQLState().equals(PSQL_UNIQUE_VIOLATION_ERROR))) {
                throw new ExistsStorageException(uuid);
            }
            throw new StorageException(e);
        }
    }

    public <R> R executeQuery(String queryString, FunctionExecWithReturn<R> action) throws StorageException {
        Objects.requireNonNull(action);
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {
            return action.accept(preparedStatement);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
