package com.urise.webapp.storage;

import com.urise.webapp.storage.serialization.JsonStreamSerialization;
import com.urise.webapp.storage.serialization.PathStorage;

public class JsonStreamPathStorageTest extends AbstractStorageTest {
    public JsonStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new JsonStreamSerialization()));
    }
}
