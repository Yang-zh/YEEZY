package com.fangzhich.sneakerlab.order.data.entity;

import java.util.List;

/**
 * OrderEntity
 * Created by Khorium on 2016/9/30.
 */
public class OrderEntity {

    public String order_id;
    public String invoice_no;
    public String invoice_prefix;
    public String store_id;
    public String store_name;
    public String store_url;
    public String customer_id;
    public String firstname;
    public String lastname;
    public String telephone;
    public String fax;
    public String email;
    public Payment payment;
    public Shipping shipping;
    public String comment;
    public String total;
    public String order_status_id;
    public String language_id;
    public String currency_id;
    public String currency_code;
    public String currency_value;
    public String date_modified;
    public String date_added;
    public String ip;
    public List<Product> product;

    public static class Payment {
        public String firstname;
        public String lastname;
        public String company;
        public String address_1;
        public String address_2;
        public String postcode;
        public String city;
        public String zone_id;
        public String zone;
        public String zone_code;
        public String country_id;
        public String country;
        public String iso_code_2;
        public String iso_code_3;
        public String address_format;
        public String method;
    }

    public static class Shipping {
        public String firstname;
        public String lastname;
        public String company;
        public String address_1;
        public String address_2;
        public String postcode;
        public String city;
        public String zone_id;
        public String zone;
        public String zone_code;
        public String country_id;
        public String country;
        public String iso_code_2;
        public String iso_code_3;
        public String address_format;
        public String method;
    }

    public static class Product {
        public String order_product_id;
        public String order_id;
        public String product_id;
        public String name;
        public String model;
        public String quantity;
        public String price;
        public String total;
        public String tax;
        public String reward;
    }
}
