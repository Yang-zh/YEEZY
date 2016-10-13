package com.fangzhich.sneakerlab.order.data.entity;

import java.util.List;

/**
 * OrderEntity
 * Created by Khorium on 2016/9/30.
 */
public class OrderEntity {

    public String order_id;
    public String date_added;
    public Shipping shipping;
    public Payment payment;
    public String price;
    public String service_charge;
    public String total;
    public String order_status;
    public String order_comment;
    public List<Product> product;

    public static class Shipping {
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

    public static class Payment {
        public String customer_id;
        public String card_number;
        public String card_month;
        public String card_year;
        public String card_cvv;
        public String zip_code;
    }

    public static class Product {
        public String order_product_id;
        public String name;
        public String image;
        public String quantity;
        public String price;
        public String total;
        public List<Option> option;

        public static class Option {
            public String name;
            public String value;
        }
    }
}
