package com.fangzhich.yeezy.net.framework;

import java.io.PrintStream;

/**
 * Created by Khorium on 2016/9/12.
 */
public class HttpResultException extends RuntimeException{

    public String message;

    public HttpResultException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
