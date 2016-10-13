package com.fangzhich.sneakerlab.order.data.entity;

import java.util.List;

/**
 * OrderItemEntity
 * Created by Khorium on 2016/9/30.
 */
public class OrderItemEntity {
    public String order_id;
    public String order_status;
    public String date_added;
    public String total;
    public List<Product> product;

    public static class Product {
        public String order_product_id;
        public String name;
        public String image;
        public String quantity;
        public List<Option> option;
        public String price;

        public static class Option {
            public String name;
            public String value;
        }
    }
}
