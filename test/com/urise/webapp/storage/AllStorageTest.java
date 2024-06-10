package com.urise.webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        MapUuidStorageTest.class,
        MapUuidStorageTest.class,
        ObjectStreamFileStorageTest.class,
        ObjectStreamPathStorageTest.class,
        XmlStreamFileStorageTest.class,
        XmlStreamPathStorageTest.class,
        JsonStreamFileStorageTest.class,
        JsonStreamPathStorageTest.class,
        DataStreamFileStorageTest.class,
        DataStreamPathStorageTest.class
})
public final class AllStorageTest {

}
