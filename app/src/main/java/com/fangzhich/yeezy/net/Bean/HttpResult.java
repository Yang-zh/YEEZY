package com.fangzhich.yeezy.net.Bean;

/**
 * HttpResult
 * Created by Khorium on 2016/9/12.
 */
public class HttpResult<T> {
    public int status_code;
    public String message;
    public T data;
}
