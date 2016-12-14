package com.fangzhich.sneakerlab.cart.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * PlaceOrderEntity
 * Created by Khorium on 2016/12/13.
 */

public class PlaceOrderEntity implements Parcelable {

    public Order order;

    public static class Order implements Parcelable {
        public String order_id;
        public String date_added;
        public Shipping shipping;
        public String order_status;
        public String order_comment;
        public List<Totals> totals;

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

            public static final Parcelable.Creator<Shipping> CREATOR = new Parcelable.Creator<Shipping>() {
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

            public static final Parcelable.Creator<Totals> CREATOR = new Parcelable.Creator<Totals>() {
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
            dest.writeParcelable(this.shipping, flags);
            dest.writeString(this.order_status);
            dest.writeString(this.order_comment);
            dest.writeTypedList(this.totals);
        }

        public Order() {
        }

        protected Order(Parcel in) {
            this.order_id = in.readString();
            this.date_added = in.readString();
            this.shipping = in.readParcelable(Shipping.class.getClassLoader());
            this.order_status = in.readString();
            this.order_comment = in.readString();
            this.totals = in.createTypedArrayList(Totals.CREATOR);
        }

        public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
            @Override
            public Order createFromParcel(Parcel source) {
                return new Order(source);
            }

            @Override
            public Order[] newArray(int size) {
                return new Order[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.order, flags);
    }

    public PlaceOrderEntity() {
    }

    protected PlaceOrderEntity(Parcel in) {
        this.order = in.readParcelable(Order.class.getClassLoader());
    }

    public static final Parcelable.Creator<PlaceOrderEntity> CREATOR = new Parcelable.Creator<PlaceOrderEntity>() {
        @Override
        public PlaceOrderEntity createFromParcel(Parcel source) {
            return new PlaceOrderEntity(source);
        }

        @Override
        public PlaceOrderEntity[] newArray(int size) {
            return new PlaceOrderEntity[size];
        }
    };
}
