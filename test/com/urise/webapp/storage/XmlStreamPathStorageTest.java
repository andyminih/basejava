package com.urise.webapp.storage;

import com.urise.webapp.storage.serialization.PathStorage;
import com.urise.webapp.storage.serialization.XmlStreamSerialization;

public class XmlStreamPathStorageTest extends AbstractStorageTest {
    public XmlStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new XmlStreamSerialization()));
    }
}
