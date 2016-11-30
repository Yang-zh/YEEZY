package com.fangzhich.ivankajingle.order.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * OrderEntity
 * Created by Khorium on 2016/9/30.
 */
public class OrderEntity implements Parcelable {

    public String order_id;
    public String date_added;
    public Address address;
    public Shipping shipping;
    public Payment payment;
    public String order_status;
    public String order_comment;
    public List<Product> product;
    public List<Totals> totals;

    public static class Address implements Parcelable {
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.address_id);
            dest.writeString(this.fullname);
            dest.writeString(this.phone);
            dest.writeString(this.company);
            dest.writeString(this.address_1);
            dest.writeString(this.suite);
            dest.writeString(this.postcode);
            dest.writeString(this.city);
            dest.writeString(this.zone_id);
            dest.writeString(this.zone);
            dest.writeString(this.zone_code);
            dest.writeString(this.country_id);
            dest.writeString(this.country);
        }

        public Address() {
        }

        protected Address(Parcel in) {
            this.address_id = in.readString();
            this.fullname = in.readString();
            this.phone = in.readString();
            this.company = in.readString();
            this.address_1 = in.readString();
            this.suite = in.readString();
            this.postcode = in.readString();
            this.city = in.readString();
            this.zone_id = in.readString();
            this.zone = in.readString();
            this.zone_code = in.readString();
            this.country_id = in.readString();
            this.country = in.readString();
        }

        public static final Creator<Address> CREATOR = new Creator<Address>() {
            @Override
            public Address createFromParcel(Parcel source) {
                return new Address(source);
            }

            @Override
            public Address[] newArray(int size) {
                return new Address[size];
            }
        };
    }

    public static class Shipping implements Parcelable {
        public String code;
        public String title;
        public String cost;
        public String text;
        public String sort_order;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.code);
            dest.writeString(this.title);
            dest.writeString(this.cost);
            dest.writeString(this.text);
            dest.writeString(this.sort_order);
        }

        public Shipping() {
        }

        protected Shipping(Parcel in) {
            this.code = in.readString();
            this.title = in.readString();
            this.cost = in.readString();
            this.text = in.readString();
            this.sort_order = in.readString();
        }

        public static final Creator<Shipping> CREATOR = new Creator<Shipping>() {
            @Override
            public Shipping createFromParcel(Parcel source) {
                return new Shipping(source);
            }

            @Override
            public Shipping[] newArray(int size) {
                return new Shipping[size];
            }
        };
    }

    public static class Payment implements Parcelable {
        public String customer_id;
        public String card_number;
        public String card_month;
        public String card_year;
        public String card_cvv;
        public String zip_code;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.customer_id);
            dest.writeString(this.card_number);
            dest.writeString(this.card_month);
            dest.writeString(this.card_year);
            dest.writeString(this.card_cvv);
            dest.writeString(this.zip_code);
        }

        public Payment() {
        }

        protected Payment(Parcel in) {
            this.customer_id = in.readString();
            this.card_number = in.readString();
            this.card_month = in.readString();
            this.card_year = in.readString();
            this.card_cvv = in.readString();
            this.zip_code = in.readString();
        }

        public static final Creator<Payment> CREATOR = new Creator<Payment>() {
            @Override
            public Payment createFromParcel(Parcel source) {
                return new Payment(source);
            }

            @Override
            public Payment[] newArray(int size) {
                return new Payment[size];
            }
        };
    }

    public static class Product implements Parcelable {
        public String order_product_id;
        public String name;
        public String image;
        public String quantity;
        public String price;
        public String total;
        public String review_status;
        public List<Option> option;

        public static class Option {
            public String name;
            public String value;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.order_product_id);
            dest.writeString(this.name);
            dest.writeString(this.image);
            dest.writeString(this.quantity);
            dest.writeString(this.price);
            dest.writeString(this.total);
            dest.writeString(this.review_status);
            dest.writeList(this.option);
        }

        public Product() {
        }

        protected Product(Parcel in) {
            this.order_product_id = in.readString();
            this.name = in.readString();
            this.image = in.readString();
            this.quantity = in.readString();
            this.price = in.readString();
            this.total = in.readString();
            this.review_status = in.readString();
            this.option = new ArrayList<Option>();
            in.readList(this.option, Option.class.getClassLoader());
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

    public static class Totals implements Parcelable {
        public String order_total_id;
        public String order_id;
        public String code;
        public String title;
        public String value;
        public String sort_order;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.order_total_id);
            dest.writeString(this.order_id);
            dest.writeString(this.code);
            dest.writeString(this.title);
            dest.writeString(this.value);
            dest.writeString(this.sort_order);
        }

        public Totals() {
        }

        protected Totals(Parcel in) {
            this.order_total_id = in.readString();
            this.order_id = in.readString();
            this.code = in.readString();
            this.title = in.readString();
            this.value = in.readString();
            this.sort_order = in.readString();
        }

        public static final Creator<Totals> CREATOR = new Creator<Totals>() {
            @Override
            public Totals createFromParcel(Parcel source) {
                return new Totals(source);
            }

            @Override
            public Totals[] newArray(int size) {
                return new Totals[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.order_id);
        dest.writeString(this.date_added);
        dest.writeParcelable(this.address, flags);
        dest.writeParcelable(this.shipping, flags);
        dest.writeParcelable(this.payment, flags);
        dest.writeString(this.order_status);
        dest.writeString(this.order_comment);
        dest.writeList(this.product);
        dest.writeList(this.totals);
    }

    public OrderEntity() {
    }

    protected OrderEntity(Parcel in) {
        this.order_id = in.readString();
        this.date_added = in.readString();
        this.address = in.readParcelable(Address.class.getClassLoader());
        this.shipping = in.readParcelable(Shipping.class.getClassLoader());
        this.payment = in.readParcelable(Payment.class.getClassLoader());
        this.order_status = in.readString();
        this.order_comment = in.readString();
        this.product = new ArrayList<Product>();
        in.readList(this.product, Product.class.getClassLoader());
        this.totals = new ArrayList<Totals>();
        in.readList(this.totals, Totals.class.getClassLoader());
    }

    public static final Parcelable.Creator<OrderEntity> CREATOR = new Parcelable.Creator<OrderEntity>() {
        @Override
        public OrderEntity createFromParcel(Parcel source) {
            return new OrderEntity(source);
        }

        @Override
        public OrderEntity[] newArray(int size) {
            return new OrderEntity[size];
        }
    };
}
