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

    public Address address;
    public Payment payment;
    public List<Product> products;
    public List<Shiping> shiping;
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
        public String rating;
        public String tax_class_id;
        public List<Option> options;

        public static class Option implements Parcelable {
            public String product_option_id;
            public String product_option_value_id;
            public String name;
            public String value;
            public String type;

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.product_option_id);
                dest.writeString(this.product_option_value_id);
                dest.writeString(this.name);
                dest.writeString(this.value);
                dest.writeString(this.type);
            }

            public Option() {
            }

            protected Option(Parcel in) {
                this.product_option_id = in.readString();
                this.product_option_value_id = in.readString();
                this.name = in.readString();
                this.value = in.readString();
                this.type = in.readString();
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
            dest.writeString(this.product_id);
            dest.writeString(this.name);
            dest.writeString(this.image);
            dest.writeString(this.model);
            dest.writeString(this.quantity);
            dest.writeByte(this.stock ? (byte) 1 : (byte) 0);
            dest.writeString(this.shipping);
            dest.writeString(this.original_price);
            dest.writeString(this.special_price);
            dest.writeString(this.reward);
            dest.writeString(this.rating);
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
            this.reward = in.readString();
            this.rating = in.readString();
            this.tax_class_id = in.readString();
            this.options = new ArrayList<Option>();
            in.readList(this.options, Option.class.getClassLoader());
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

    public static class Shiping implements Parcelable {
        public String type;
        public String title;
        public String code;
        public String cost;
        public String tax_class_id;
        public String text;
        public String sort_order;
        public boolean error;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.type);
            dest.writeString(this.title);
            dest.writeString(this.code);
            dest.writeString(this.cost);
            dest.writeString(this.tax_class_id);
            dest.writeString(this.text);
            dest.writeString(this.sort_order);
            dest.writeByte(this.error ? (byte) 1 : (byte) 0);
        }

        public Shiping() {
        }

        protected Shiping(Parcel in) {
            this.type = in.readString();
            this.title = in.readString();
            this.code = in.readString();
            this.cost = in.readString();
            this.tax_class_id = in.readString();
            this.text = in.readString();
            this.sort_order = in.readString();
            this.error = in.readByte() != 0;
        }

        public static final Creator<Shiping> CREATOR = new Creator<Shiping>() {
            @Override
            public Shiping createFromParcel(Parcel source) {
                return new Shiping(source);
            }

            @Override
            public Shiping[] newArray(int size) {
                return new Shiping[size];
            }
        };
    }

    public static class Totals implements Parcelable {
        public String title;
        public String text;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.title);
            dest.writeString(this.text);
        }

        public Totals() {
        }

        protected Totals(Parcel in) {
            this.title = in.readString();
            this.text = in.readString();
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
        dest.writeParcelable(this.address, flags);
        dest.writeParcelable(this.payment, flags);
        dest.writeList(this.products);
        dest.writeList(this.shiping);
        dest.writeList(this.totals);
    }

    public CartEntity() {
    }

    protected CartEntity(Parcel in) {
        this.address = in.readParcelable(Address.class.getClassLoader());
        this.payment = in.readParcelable(Payment.class.getClassLoader());
        this.products = new ArrayList<Product>();
        in.readList(this.products, Product.class.getClassLoader());
        this.shiping = new ArrayList<Shiping>();
        in.readList(this.shiping, Shiping.class.getClassLoader());
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
