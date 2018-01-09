package com.rest.fileupload.rp.restfileuploadrp.storage;

public class FilesNotFoundException extends FileExcept {

    public FilesNotFoundException(String message) {
        super(message);
    }

    public FilesNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}