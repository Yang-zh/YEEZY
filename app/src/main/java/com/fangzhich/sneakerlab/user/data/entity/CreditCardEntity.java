package com.fangzhich.sneakerlab.user.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * CreditCardEntity
 * Created by Khorium on 2016/9/30.
 */
public class CreditCardEntity implements Parcelable {
    public String credit_id;
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
        dest.writeString(this.credit_id);
        dest.writeString(this.customer_id);
        dest.writeString(this.card_number);
        dest.writeString(this.card_month);
        dest.writeString(this.card_year);
        dest.writeString(this.card_cvv);
        dest.writeString(this.zip_code);
    }

    public CreditCardEntity() {
    }

    protected CreditCardEntity(Parcel in) {
        this.credit_id = in.readString();
        this.customer_id = in.readString();
        this.card_number = in.readString();
        this.card_month = in.readString();
        this.card_year = in.readString();
        this.card_cvv = in.readString();
        this.zip_code = in.readString();
    }

    public static final Parcelable.Creator<CreditCardEntity> CREATOR = new Parcelable.Creator<CreditCardEntity>() {
        @Override
        public CreditCardEntity createFromParcel(Parcel source) {
            return new CreditCardEntity(source);
        }

        @Override
        public CreditCardEntity[] newArray(int size) {
            return new CreditCardEntity[size];
        }
    };
}
