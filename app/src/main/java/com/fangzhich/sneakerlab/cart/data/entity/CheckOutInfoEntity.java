package com.fangzhich.sneakerlab.cart.data.entity;

import java.util.List;

/**
 * CheckOutInfoEntity
 * Created by Khorium on 2016/12/8.
 */

public class CheckOutInfoEntity {

    public Address address;
    public Shiping shiping;
    public Payment payment;
    public List<Products> products;
    public List<Totals> totals;

    public static class Address {
        public String address_id;
        public String fullname;
        public String phone;
        public String company;
        public String address_1;
        public String suite;
        public String postcode;
        public String city;
        public String zone_id;
        public String zone;
        public String zone_code;
        public String country_id;
        public String country;
    }

    public static class Shiping {
        public String code;
        public String title;
        public String cost;
        public String text;
        public String sort_order;
    }

    public static class Payment {
        public String customer_id;
        public String card_number;
        public String card_month;
        public String card_year;
        public String card_cvv;
        public String zip_code;
    }

    public static class Products {
        public String cart_id;
        public String product_id;
        public String name;
        public String image;
        public String model;
        public String quantity;
        public boolean stock;
        public String shipping;
        public String original_price;
        public String special_price;
        public String reward;
        public String reviews;
        public String rating;
        public String tax_class_id;
        public List<Options> options;

        public static class Options {
            public String product_option_id;
            public String product_option_value_id;
            public String name;
            public String value;
            public String type;
        }
    }

    public static class Totals {
        public String title;
        public int value;
        public String text;
    }
}
