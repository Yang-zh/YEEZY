package com.fangzhich.sneakerlab.product.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * ProductEntity
 * Created by Khorium on 2016/9/14.
 */
public class ProductEntity implements Parcelable {

    public String product_id;
    public String name;
    public String description;
    public String tag;
    public String model;
    public String location;
    public String quantity;
    public String stock_status;
    public String manufacturer_id;
    public String manufacturer;
    public String original_price;
    public String special_price;
    public String reward;
    public String sales_volume;
    public int points;
    public String collections;
    public String shares;
    public String tax_class_id;
    public String date_available;
    public String weight;
    public String weight_class_id;
    public String length;
    public String width;
    public String height;
    public String length_class_id;
    public String subtract;
    public int rating;
    public String reviews;
    public String minimum;
    public String sort_order;
    public String status;
    public String date_added;
    public String date_modified;
    public String viewed;
    public ShippingInfo shipping_info;
    public List<String> images;
    public List<Option> options;

    public static class ShippingInfo implements Parcelable {
        public String EstimatedShipping;
        public String Availability;
        public String EstimatedArrival;
        public String ShipsFrom;
        public String ReturnPolicy;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.EstimatedShipping);
            dest.writeString(this.Availability);
            dest.writeString(this.EstimatedArrival);
            dest.writeString(this.ShipsFrom);
            dest.writeString(this.ReturnPolicy);
        }

        public ShippingInfo() {
        }

        protected ShippingInfo(Parcel in) {
            this.EstimatedShipping = in.readString();
            this.Availability = in.readString();
            this.EstimatedArrival = in.readString();
            this.ShipsFrom = in.readString();
            this.ReturnPolicy = in.readString();
        }

        public static final Creator<ShippingInfo> CREATOR = new Creator<ShippingInfo>() {
            @Override
            public ShippingInfo createFromParcel(Parcel source) {
                return new ShippingInfo(source);
            }

            @Override
            public ShippingInfo[] newArray(int size) {
                return new ShippingInfo[size];
            }
        };
    }

    public static class Option implements Parcelable {
        public String product_option_id;
        public String option_id;
        public String name;
        public String type;
        public String value;
        public String required;
        public List<ProductOption> product_option_value;

        public static class ProductOption implements Parcelable {
            public String product_option_value_id;
            public String option_value_id;
            public String name;
            public String image;
            public String quantity;
            public String subtract;
            public String price;
            public String price_prefix;
            public String weight;
            public String weight_prefix;

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.product_option_value_id);
                dest.writeString(this.option_value_id);
                dest.writeString(this.name);
                dest.writeString(this.image);
                dest.writeString(this.quantity);
                dest.writeString(this.subtract);
                dest.writeString(this.price);
                dest.writeString(this.price_prefix);
                dest.writeString(this.weight);
                dest.writeString(this.weight_prefix);
            }

            public ProductOption() {
            }

            protected ProductOption(Parcel in) {
                this.product_option_value_id = in.readString();
                this.option_value_id = in.readString();
                this.name = in.readString();
                this.image = in.readString();
                this.quantity = in.readString();
                this.subtract = in.readString();
                this.price = in.readString();
                this.price_prefix = in.readString();
                this.weight = in.readString();
                this.weight_prefix = in.readString();
            }

            public static final Creator<ProductOption> CREATOR = new Creator<ProductOption>() {
                @Override
                public ProductOption createFromParcel(Parcel source) {
                    return new ProductOption(source);
                }

                @Override
                public ProductOption[] newArray(int size) {
                    return new ProductOption[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.product_option_id);
            dest.writeString(this.option_id);
            dest.writeString(this.name);
            dest.writeString(this.type);
            dest.writeString(this.value);
            dest.writeString(this.required);
            dest.writeList(this.product_option_value);
        }

        public Option() {
        }

        protected Option(Parcel in) {
            this.product_option_id = in.readString();
            this.option_id = in.readString();
            this.name = in.readString();
            this.type = in.readString();
            this.value = in.readString();
            this.required = in.readString();
            this.product_option_value = new ArrayList<ProductOption>();
            in.readList(this.product_option_value, ProductOption.class.getClassLoader());
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
        dest.writeString(this.product_id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.tag);
        dest.writeString(this.model);
        dest.writeString(this.location);
        dest.writeString(this.quantity);
        dest.writeString(this.stock_status);
        dest.writeString(this.manufacturer_id);
        dest.writeString(this.manufacturer);
        dest.writeString(this.original_price);
        dest.writeString(this.special_price);
        dest.writeString(this.reward);
        dest.writeString(this.sales_volume);
        dest.writeInt(this.points);
        dest.writeString(this.collections);
        dest.writeString(this.shares);
        dest.writeString(this.tax_class_id);
        dest.writeString(this.date_available);
        dest.writeString(this.weight);
        dest.writeString(this.weight_class_id);
        dest.writeString(this.length);
        dest.writeString(this.width);
        dest.writeString(this.height);
        dest.writeString(this.length_class_id);
        dest.writeString(this.subtract);
        dest.writeInt(this.rating);
        dest.writeString(this.reviews);
        dest.writeString(this.minimum);
        dest.writeString(this.sort_order);
        dest.writeString(this.status);
        dest.writeString(this.date_added);
        dest.writeString(this.date_modified);
        dest.writeString(this.viewed);
        dest.writeParcelable(this.shipping_info, flags);
        dest.writeStringList(this.images);
        dest.writeList(this.options);
    }

    public ProductEntity() {
    }

    protected ProductEntity(Parcel in) {
        this.product_id = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.tag = in.readString();
        this.model = in.readString();
        this.location = in.readString();
        this.quantity = in.readString();
        this.stock_status = in.readString();
        this.manufacturer_id = in.readString();
        this.manufacturer = in.readString();
        this.original_price = in.readString();
        this.special_price = in.readString();
        this.reward = in.readString();
        this.sales_volume = in.readString();
        this.points = in.readInt();
        this.collections = in.readString();
        this.shares = in.readString();
        this.tax_class_id = in.readString();
        this.date_available = in.readString();
        this.weight = in.readString();
        this.weight_class_id = in.readString();
        this.length = in.readString();
        this.width = in.readString();
        this.height = in.readString();
        this.length_class_id = in.readString();
        this.subtract = in.readString();
        this.rating = in.readInt();
        this.reviews = in.readString();
        this.minimum = in.readString();
        this.sort_order = in.readString();
        this.status = in.readString();
        this.date_added = in.readString();
        this.date_modified = in.readString();
        this.viewed = in.readString();
        this.shipping_info = in.readParcelable(ShippingInfo.class.getClassLoader());
        this.images = in.createStringArrayList();
        this.options = new ArrayList<Option>();
        in.readList(this.options, Option.class.getClassLoader());
    }

    public static final Creator<ProductEntity> CREATOR = new Creator<ProductEntity>() {
        @Override
        public ProductEntity createFromParcel(Parcel source) {
            return new ProductEntity(source);
        }

        @Override
        public ProductEntity[] newArray(int size) {
            return new ProductEntity[size];
        }
    };
}
