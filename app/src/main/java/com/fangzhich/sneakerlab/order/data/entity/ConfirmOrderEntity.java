package com.fangzhich.sneakerlab.order.data.entity;

/**
 * ConfirmOrderEntity
 * Created by Khorium on 2016/10/10.
 */
public class ConfirmOrderEntity {
    public boolean error;
    public Order order;

    public static class Order {
        public String OrderID;
        public String Currency;
        public String Amount;
        public String Code;
        public String Status;
        public String MD5info;
    }
}
