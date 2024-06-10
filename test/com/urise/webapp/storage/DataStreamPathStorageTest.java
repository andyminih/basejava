package com.urise.webapp.storage;

import com.urise.webapp.storage.serialization.DataStreamSerialization;
import com.urise.webapp.storage.serialization.PathStorage;

public class DataStreamPathStorageTest extends AbstractStorageTest {
    public DataStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataStreamSerialization()));
    }
}
