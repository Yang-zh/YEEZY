package com.fangzhich.yeezy.cart.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * CartEntity
 * Created by Khorium on 2016/9/21.
 */
public class CartEntity {


    public ArrayList<CartItem> products;
    public ArrayList<Totals> totals;

    public static class CartItem implements Parcelable {

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.cart_id);
            dest.writeString(this.product_id);
            dest.writeString(this.name);
            dest.writeString(this.model);
            dest.writeString(this.quantity);
            dest.writeByte(this.stock ? (byte) 1 : (byte) 0);
            dest.writeString(this.shipping);
            dest.writeString(this.price);
            dest.writeString(this.total);
            dest.writeInt(this.reward);
            dest.writeList(this.options);
        }

        public CartItem() {
        }

        protected CartItem(Parcel in) {
            this.cart_id = in.readString();
            this.product_id = in.readString();
            this.name = in.readString();
            this.model = in.readString();
            this.quantity = in.readString();
            this.stock = in.readByte() != 0;
            this.shipping = in.readString();
            this.price = in.readString();
            this.total = in.readString();
            this.reward = in.readInt();
            this.options = new ArrayList<Option>();
            in.readList(this.options, Option.class.getClassLoader());
        }

        public static final Parcelable.Creator<CartItem> CREATOR = new Parcelable.Creator<CartItem>() {
            @Override
            public CartItem createFromParcel(Parcel source) {
                return new CartItem(source);
            }

            @Override
            public CartItem[] newArray(int size) {
                return new CartItem[size];
            }
        };
    }

    static class Totals {
        public String title;
        public String text;
    }
}
