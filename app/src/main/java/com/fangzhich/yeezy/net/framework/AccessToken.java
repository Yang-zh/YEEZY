package com.fangzhich.yeezy.net.framework;

/**
 * Oauth Token
 * Created by Khorium on 2016/8/30.
 */
public class AccessToken {

    public AccessToken() {
    }

    public AccessToken(String tokenType, String accessToken) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
    }

    private String tokenType;
    private String accessToken;

    public String getTokenType() {
        return tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
