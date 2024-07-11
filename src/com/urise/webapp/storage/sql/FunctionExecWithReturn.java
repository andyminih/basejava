package com.urise.webapp.storage.sql;

import java.sql.SQLException;

@FunctionalInterface
public interface FunctionExecWithReturn<R> {
    R accept() throws SQLException;
}
