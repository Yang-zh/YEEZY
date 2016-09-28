package com.fangzhich.yeezy.cart.data.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * CartEntity
 * Created by Khorium on 2016/9/21.
 */
public class CartEntity {


    public ArrayList<CartItem> products;
    public ArrayList<Totals> totals;

    public static class CartItem {
        public String cart_id;
        public String product_id;
        public String name;
        public String model;
        public String quantity;
        public boolean stock;
        public String shipping;
        public String price;
        public String total;
        public int reward;
        public List<Option> options;

        public static class Option {
            public String product_option_id;
            public String product_option_value_id;
            public String name;
            public String value;
            public String type;
        }
    }

    static class Totals {
        public String title;
        public String text;
    }
}