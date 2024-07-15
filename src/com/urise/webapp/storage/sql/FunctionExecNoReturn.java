package com.urise.webapp.storage.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface FunctionExecNoReturn {
    void accept(PreparedStatement preparedStatement) throws SQLException;
}
