package com.fangzhich.yeezy.net.framework;

/**
 *
 * Created by Khorium on 2016/8/30.
 */
public class HttpException extends RuntimeException {

    public HttpException(String detailMessage) {
        super(getApiExceptionMessage(detailMessage));
    }

    private static String getApiExceptionMessage(String error){
        String message = "";
        switch (error) {
            case "a":
                message = "aaaa";
                break;
            case "b":
                message = "bbbb";
                break;
            default:
                message = "未知错误";

        }
        return message;
    }
}
