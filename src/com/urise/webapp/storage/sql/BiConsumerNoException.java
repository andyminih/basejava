package com.urise.webapp.storage.sql;

import java.sql.SQLException;

public interface BiConsumerNoException<T, U> {
    void accept(T t, U u) throws SQLException;
}
