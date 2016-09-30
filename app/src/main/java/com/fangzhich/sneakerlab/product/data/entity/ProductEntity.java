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
    public int original_price;
    public int special_price;
    public String reward;
    public int points;
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
    public List<String> images;
    public ArrayList<Option> options;

    public static class Option{

        public String product_option_id;
        public String option_id;
        public String name;
        public String type;
        public String value;
        public String required;
        public List<ProductOption> product_option_value;

        public static class ProductOption {
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
        }
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
        dest.writeInt(this.special_price);
        dest.writeInt(this.original_price);
        dest.writeString(this.reward);
        dest.writeInt(this.points);
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
        dest.writeStringList(this.images);
    }

    private ProductEntity(Parcel in) {
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
        this.original_price = in.readInt();
        this.special_price = in.readInt();
        this.reward = in.readString();
        this.points = in.readInt();
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
        this.images = in.createStringArrayList();
    }

    public static final Parcelable.Creator<ProductEntity> CREATOR = new Parcelable.Creator<ProductEntity>() {
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
