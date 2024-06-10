package com.urise.webapp.storage;

import com.urise.webapp.storage.serialization.FileStorage;
import com.urise.webapp.storage.serialization.XmlStreamSerialization;

public class XmlStreamFileStorageTest extends AbstractStorageTest {
    public XmlStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new XmlStreamSerialization()));
    }
}
