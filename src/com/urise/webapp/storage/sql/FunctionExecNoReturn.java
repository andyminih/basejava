package com.urise.webapp.storage.sql;

import java.sql.SQLException;

@FunctionalInterface
public interface FunctionExecNoReturn {
    void accept() throws SQLException;
}
