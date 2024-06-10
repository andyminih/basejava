package com.urise.webapp.storage;

import com.urise.webapp.storage.serialization.DataStreamSerialization;
import com.urise.webapp.storage.serialization.FileStorage;

public class DataStreamFileStorageTest extends AbstractStorageTest {
    public DataStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new DataStreamSerialization()));
    }
}
