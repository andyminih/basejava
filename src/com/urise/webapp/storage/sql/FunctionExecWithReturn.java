package com.urise.webapp.storage.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface FunctionExecWithReturn<R> {
    R accept(PreparedStatement preparedStatement) throws SQLException;
}
