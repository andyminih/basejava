package com.urise.webapp.storage.sql;

import com.urise.webapp.exception.ExistsStorageException;
import com.urise.webapp.exception.StorageException;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;

public class ExceptionUtil {
    private final static String PSQL_UNIQUE_VIOLATION_ERROR = "23505";

    private ExceptionUtil() {
    }

    public static StorageException convertException(SQLException e) {
        if (e instanceof PSQLException
                && (e.getSQLState().equals(PSQL_UNIQUE_VIOLATION_ERROR))) {
            throw new ExistsStorageException(e.getMessage());
        }
        return new StorageException(e);
    }
}
