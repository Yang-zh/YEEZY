package com.fangzhich.sneakerlab.user.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * AddressEntity
 * Created by Khorium on 2016/12/10.
 */

public class AddressEntity implements Parcelable {
    public String address_id;
    public String fullname;
    public String company;
    public String address_1;
    public String address_2;
    public String suite;
    public String postcode;
    public String city;
    public String zone_id;
    public String zone;
    public String zone_code;
    public String country_id;
    public String country;
    public String iso_code_2;
    public String iso_code_3;
    public String address_format;
    public String phone;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address_id);
        dest.writeString(this.fullname);
        dest.writeString(this.company);
        dest.writeString(this.address_1);
        dest.writeString(this.address_2);
        dest.writeString(this.suite);
        dest.writeString(this.postcode);
        dest.writeString(this.city);
        dest.writeString(this.zone_id);
        dest.writeString(this.zone);
        dest.writeString(this.zone_code);
        dest.writeString(this.country_id);
        dest.writeString(this.country);
        dest.writeString(this.iso_code_2);
        dest.writeString(this.iso_code_3);
        dest.writeString(this.address_format);
        dest.writeString(this.phone);
    }

    public AddressEntity() {
    }

    protected AddressEntity(Parcel in) {
        this.address_id = in.readString();
        this.fullname = in.readString();
        this.company = in.readString();
        this.address_1 = in.readString();
        this.address_2 = in.readString();
        this.suite = in.readString();
        this.postcode = in.readString();
        this.city = in.readString();
        this.zone_id = in.readString();
        this.zone = in.readString();
        this.zone_code = in.readString();
        this.country_id = in.readString();
        this.country = in.readString();
        this.iso_code_2 = in.readString();
        this.iso_code_3 = in.readString();
        this.address_format = in.readString();
        this.phone = in.readString();
    }

    public static final Parcelable.Creator<AddressEntity> CREATOR = new Parcelable.Creator<AddressEntity>() {
        @Override
        public AddressEntity createFromParcel(Parcel source) {
            return new AddressEntity(source);
        }

        @Override
        public AddressEntity[] newArray(int size) {
            return new AddressEntity[size];
        }
    };
}
