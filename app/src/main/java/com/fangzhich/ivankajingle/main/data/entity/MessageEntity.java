package com.fangzhich.ivankajingle.main.data.entity;

/**
 * MessageEntity
 * Created by Khorium on 2016/10/17.
 */
public class MessageEntity {
    public String messages_id;
    public String customer_id;
    public String date_added;
    public String fullname;
    public String equipment_token;
    public String text;
    public int type;

    public MessageEntity(String text, int type) {
        this.text = text;
        this.type = type;
    }
}
