package com.fangzhich.sneakerlab.cart.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * CartEntity
 * Created by Khorium on 2016/9/21.
 */
public class CartEntity implements Parcelable {

    public List<Product> products;
    public List<Address> address;
    public List<Shiping> shiping;
    public List<Payment> payment;
    public List<Totals> totals;

    public static class Product implements Parcelable {
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
        public int reward;
        public int rating;
        public String tax_class_id;
        public List<Options> options;

        public static class Options {
            public String product_option_id;
            public String product_option_value_id;
            public String name;
            public String value;
            public String type;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.cart_id);
            dest.writeString(this.product_id);
            dest.writeString(this.name);
            dest.writeString(this.image);
            dest.writeString(this.model);
            dest.writeString(this.quantity);
            dest.writeByte(this.stock ? (byte) 1 : (byte) 0);
            dest.writeString(this.shipping);
            dest.writeString(this.original_price);
            dest.writeString(this.special_price);
            dest.writeInt(this.reward);
            dest.writeInt(this.rating);
            dest.writeString(this.tax_class_id);
            dest.writeList(this.options);
        }

        public Product() {
        }

        protected Product(Parcel in) {
            this.cart_id = in.readString();
            this.product_id = in.readString();
            this.name = in.readString();
            this.image = in.readString();
            this.model = in.readString();
            this.quantity = in.readString();
            this.stock = in.readByte() != 0;
            this.shipping = in.readString();
            this.original_price = in.readString();
            this.special_price = in.readString();
            this.reward = in.readInt();
            this.rating = in.readInt();
            this.tax_class_id = in.readString();
            this.options = new ArrayList<Options>();
            in.readList(this.options, Options.class.getClassLoader());
        }

        public static final Creator<Product> CREATOR = new Creator<Product>() {
            @Override
            public Product createFromParcel(Parcel source) {
                return new Product(source);
            }

            @Override
            public Product[] newArray(int size) {
                return new Product[size];
            }
        };
    }

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
        public String type;
        public String title;
        public String code;
        public String cost;
        public String tax_class_id;
        public String text;
        public String sort_order;
        public boolean error;
    }

    public static class Payment {
        public String code;
        public String title;
        public String image;
        public String sort_order;
    }

    public static class Totals {
        public String title;
        public String text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.products);
        dest.writeList(this.address);
        dest.writeList(this.shiping);
        dest.writeList(this.payment);
        dest.writeList(this.totals);
    }

    public CartEntity() {
    }

    protected CartEntity(Parcel in) {
        this.products = new ArrayList<Product>();
        in.readList(this.products, Product.class.getClassLoader());
        this.address = new ArrayList<Address>();
        in.readList(this.address, Address.class.getClassLoader());
        this.shiping = new ArrayList<Shiping>();
        in.readList(this.shiping, Shiping.class.getClassLoader());
        this.payment = new ArrayList<Payment>();
        in.readList(this.payment, Payment.class.getClassLoader());
        this.totals = new ArrayList<Totals>();
        in.readList(this.totals, Totals.class.getClassLoader());
    }

    public static final Parcelable.Creator<CartEntity> CREATOR = new Parcelable.Creator<CartEntity>() {
        @Override
        public CartEntity createFromParcel(Parcel source) {
            return new CartEntity(source);
        }

        @Override
        public CartEntity[] newArray(int size) {
            return new CartEntity[size];
        }
    };
}
