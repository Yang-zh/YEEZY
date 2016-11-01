package com.fangzhich.sneakerlab.base.data.net;

/**
 * HttpResultNoLoginException
 * Created by Khorium on 2016/11/1.
 */
public class HttpResultNoLoginException extends RuntimeException {

    public String message;

    public HttpResultNoLoginException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
