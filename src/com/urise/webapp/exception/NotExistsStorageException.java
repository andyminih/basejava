package com.urise.webapp.exception;

public class NotExistsStorageException extends StorageException {

    public NotExistsStorageException(String uuid) {
        super(uuid, "Резюме  " + uuid + " не существует.");
    }
}
