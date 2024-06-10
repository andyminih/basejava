package com.urise.webapp.storage;

import com.urise.webapp.storage.serialization.FileStorage;
import com.urise.webapp.storage.serialization.JsonStreamSerialization;

public class JsonStreamFileStorageTest extends AbstractStorageTest {
    public JsonStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new JsonStreamSerialization()));
    }
}
