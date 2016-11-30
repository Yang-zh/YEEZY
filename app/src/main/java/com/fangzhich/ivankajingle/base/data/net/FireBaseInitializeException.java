package com.fangzhich.ivankajingle.base.data.net;

/**
 * FireBaseInitializeException
 * Created by Khorium on 2016/11/1.
 */
public class FireBaseInitializeException extends Throwable {

    public String message;

    public FireBaseInitializeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
