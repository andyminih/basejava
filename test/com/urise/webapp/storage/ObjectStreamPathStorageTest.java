package com.urise.webapp.storage;

import com.urise.webapp.storage.serialization.ObjectStreamSerialization;
import com.urise.webapp.storage.serialization.PathStorage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamSerialization()));
    }
}
