package com.urise.webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        StorageTest.class,
        SortedStorageTest.class,
        ListStorageTest.class,
        MapUuidStorageTest.class,
        MapUuidStorageTest.class
})
public final class AllStorageTest {

}
