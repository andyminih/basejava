package com.urise.webapp.storage.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface SqlTransaction<R> {
    R execute(Connection connection) throws SQLException;
}
