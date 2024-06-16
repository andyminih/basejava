package com.urise.webapp.storage.serialization;

import java.io.IOException;

@FunctionalInterface
public interface FunctionWriter<T> {
    void accept(T t) throws IOException;
}