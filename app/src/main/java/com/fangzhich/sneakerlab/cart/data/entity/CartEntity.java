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
    public List<Cart> cart;
    public List<CartBack> cartback;

    public static class Cart implements Parcelable {
        public String cart_id;
        public String api_id;
        public String customer_id;
        public String session_id;
        public String product_id;
        public String recurring_id;
        public String quantity;
        public String date_added;
        public String name;
        public String image;
        public String original_price;
        public String special_price;
        public List<Option> option;

        public static class Option implements Parcelable {
            public String option_key_id;
            public String option_key;
            public String option_value_id;
            public String option_value;

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.option_key_id);
                dest.writeString(this.option_key);
                dest.writeString(this.option_value_id);
                dest.writeString(this.option_value);
            }

            public Option() {
            }

            protected Option(Parcel in) {
                this.option_key_id = in.readString();
                this.option_key = in.readString();
                this.option_value_id = in.readString();
                this.option_value = in.readString();
            }

            public static final Creator<Option> CREATOR = new Creator<Option>() {
                @Override
                public Option createFromParcel(Parcel source) {
                    return new Option(source);
                }

                @Override
                public Option[] newArray(int size) {
                    return new Option[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.cart_id);
            dest.writeString(this.api_id);
            dest.writeString(this.customer_id);
            dest.writeString(this.session_id);
            dest.writeString(this.product_id);
            dest.writeString(this.recurring_id);
            dest.writeString(this.quantity);
            dest.writeString(this.date_added);
            dest.writeString(this.name);
            dest.writeString(this.image);
            dest.writeString(this.original_price);
            dest.writeString(this.special_price);
            dest.writeList(this.option);
        }

        public Cart() {
        }

        protected Cart(Parcel in) {
            this.cart_id = in.readString();
            this.api_id = in.readString();
            this.customer_id = in.readString();
            this.session_id = in.readString();
            this.product_id = in.readString();
            this.recurring_id = in.readString();
            this.quantity = in.readString();
            this.date_added = in.readString();
            this.name = in.readString();
            this.image = in.readString();
            this.original_price = in.readString();
            this.special_price = in.readString();
            this.option = new ArrayList<Option>();
            in.readList(this.option, Option.class.getClassLoader());
        }

        public static final Creator<Cart> CREATOR = new Creator<Cart>() {
            @Override
            public Cart createFromParcel(Parcel source) {
                return new Cart(source);
            }

            @Override
            public Cart[] newArray(int size) {
                return new Cart[size];
            }
        };
    }

    public static class CartBack implements Parcelable {
        public String cart_back_id;
        public String customer_id;
        public String product_id;
        public String date_added;
        public String name;
        public String image;
        public String original_price;
        public String special_price;
        public List<Option> option;

        public static class Option implements Parcelable {
            public String option_key_id;
            public String option_key;
            public String option_value_id;
            public String option_value;

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.option_key_id);
                dest.writeString(this.option_key);
                dest.writeString(this.option_value_id);
                dest.writeString(this.option_value);
            }

            public Option() {
            }

            protected Option(Parcel in) {
                this.option_key_id = in.readString();
                this.option_key = in.readString();
                this.option_value_id = in.readString();
                this.option_value = in.readString();
            }

            public static final Creator<Option> CREATOR = new Creator<Option>() {
                @Override
                public Option createFromParcel(Parcel source) {
                    return new Option(source);
                }

                @Override
                public Option[] newArray(int size) {
                    return new Option[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.cart_back_id);
            dest.writeString(this.customer_id);
            dest.writeString(this.product_id);
            dest.writeString(this.date_added);
            dest.writeString(this.name);
            dest.writeString(this.image);
            dest.writeString(this.original_price);
            dest.writeString(this.special_price);
            dest.writeList(this.option);
        }

        public CartBack() {
        }

        protected CartBack(Parcel in) {
            this.cart_back_id = in.readString();
            this.customer_id = in.readString();
            this.product_id = in.readString();
            this.date_added = in.readString();
            this.name = in.readString();
            this.image = in.readString();
            this.original_price = in.readString();
            this.special_price = in.readString();
            this.option = new ArrayList<Option>();
            in.readList(this.option, Option.class.getClassLoader());
        }

        public static final Creator<CartBack> CREATOR = new Creator<CartBack>() {
            @Override
            public CartBack createFromParcel(Parcel source) {
                return new CartBack(source);
            }

            @Override
            public CartBack[] newArray(int size) {
                return new CartBack[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.cart);
        dest.writeList(this.cartback);
    }

    public CartEntity() {
    }

    protected CartEntity(Parcel in) {
        this.cart = new ArrayList<Cart>();
        in.readList(this.cart, Cart.class.getClassLoader());
        this.cartback = new ArrayList<CartBack>();
        in.readList(this.cartback, CartBack.class.getClassLoader());
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
