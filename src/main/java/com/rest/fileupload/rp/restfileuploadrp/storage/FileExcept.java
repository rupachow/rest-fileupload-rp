package com.rest.fileupload.rp.restfileuploadrp.storage;

public class FileExcept extends RuntimeException {

    public FileExcept(String message) {
        super(message);
    }

    public FileExcept(String message, Throwable cause) {
        super(message, cause);
    }
}
