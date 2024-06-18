package com.urise.webapp.storage.serialization;

import java.io.IOException;

@FunctionalInterface
public interface FunctionReader {
    void accept() throws IOException;
}