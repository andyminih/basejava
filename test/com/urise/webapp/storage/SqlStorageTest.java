package com.urise.webapp.storage;

import com.urise.webapp.storage.sql.SqlStorage;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage());
    }

}
