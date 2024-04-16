package com.urise.webapp.exception;

public class ExistsStorageException extends StorageException {
    public ExistsStorageException(String uuid) {
        super(uuid, "Резюме " + uuid + " уже существует.");
    }
}
