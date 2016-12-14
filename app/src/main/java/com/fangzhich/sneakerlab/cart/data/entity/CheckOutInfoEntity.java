package com.fangzhich.sneakerlab.cart.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.fangzhich.sneakerlab.user.data.entity.AddressEntity;
import com.fangzhich.sneakerlab.user.data.entity.CreditCardEntity;

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

        public Address(AddressEntity address) {
            this.address_id = address.address_id;
            this.fullname = address.fullname;
            this.phone = address.phone;
            this.company = address.company;
            this.address_1 = address.address_1;
            this.suite = address.suite;
            this.postcode = address.postcode;
            this.city = address.city;
            this.zone_id = address.zone_id;
            this.zone = address.zone;
            this.zone_code = address.zone_code;
            this.country_id = address.country_id;
            this.country = address.country;
        }

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

        public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>() {
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

    public static class Shiping {
        public String code;
        public String title;
        public String cost;
        public String text;
        public String sort_order;
    }

    public static class Payment implements Parcelable {
        public String customer_id;
        public String card_number;
        public String card_month;
        public String card_year;
        public String card_cvv;
        public String zip_code;
        public String credit_id;

        public Payment(CreditCardEntity card) {
            this.customer_id =card.customer_id;
            this.card_number =card.card_number;
            this.card_month =card.card_month;
            this.card_year =card.card_year;
            this.card_cvv =card.card_cvv;
            this.zip_code =card.zip_code;
            this.credit_id =card.credit_id;
        }

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
            dest.writeString(this.credit_id);
        }

        protected Payment(Parcel in) {
            this.customer_id = in.readString();
            this.card_number = in.readString();
            this.card_month = in.readString();
            this.card_year = in.readString();
            this.card_cvv = in.readString();
            this.zip_code = in.readString();
            this.credit_id = in.readString();
        }

        public static final Parcelable.Creator<Payment> CREATOR = new Parcelable.Creator<Payment>() {
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
        public float value;
        public String text;
    }
}
