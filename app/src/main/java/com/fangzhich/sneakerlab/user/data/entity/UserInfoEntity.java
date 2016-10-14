package com.fangzhich.sneakerlab.user.data.entity;

/**
 *
 * Created by Khorium on 2016/9/8.
 */
public class UserInfoEntity {
    @Override
    public String toString() {
        return "UserInfoEntity{" +
                "token='" + token + '\'' +
                ", user_info=" + user_info +
                ", shipping_address=" + shipping_address +
                '}';
    }

    public String token;
        public UserInfo user_info;
        public ShippingAddress shipping_address;

        public static class UserInfo {
            public String customer_id;
            public String customer_group_id;
            public String store_id;
            public String language_id;
            public String firstname;
            public String lastname;
            public String email;
            public String telephone;
            public String fax;
            public Object cart;
            public Object wishlist;
            public String newsletter;
            public String address_id;
            public String custom_field;
            public String ip;
            public String status;
            public String approved;
            public String safe;
            public String token;
            public String code;
            public String date_added;

            @Override
            public String toString() {
                return "UserInfo{" +
                        "customer_id='" + customer_id + '\'' +
                        ", customer_group_id='" + customer_group_id + '\'' +
                        ", store_id='" + store_id + '\'' +
                        ", language_id='" + language_id + '\'' +
                        ", firstname='" + firstname + '\'' +
                        ", lastname='" + lastname + '\'' +
                        ", email='" + email + '\'' +
                        ", telephone='" + telephone + '\'' +
                        ", fax='" + fax + '\'' +
                        ", cart=" + cart +
                        ", wishlist=" + wishlist +
                        ", newsletter='" + newsletter + '\'' +
                        ", address_id='" + address_id + '\'' +
                        ", custom_field='" + custom_field + '\'' +
                        ", ip='" + ip + '\'' +
                        ", status='" + status + '\'' +
                        ", approved='" + approved + '\'' +
                        ", safe='" + safe + '\'' +
                        ", token='" + token + '\'' +
                        ", code='" + code + '\'' +
                        ", date_added='" + date_added + '\'' +
                        '}';
            }
        }
        public static class ShippingAddress {
            public String address_id;
            public String firstname;
            public String lastname;
            public String company;
            public String address_1;
            public String address_2;
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
            public Object custom_field;

            @Override
            public String toString() {
                return "ShippingAddress{" +
                        "address_id='" + address_id + '\'' +
                        ", firstname='" + firstname + '\'' +
                        ", lastname='" + lastname + '\'' +
                        ", company='" + company + '\'' +
                        ", address_1='" + address_1 + '\'' +
                        ", address_2='" + address_2 + '\'' +
                        ", postcode='" + postcode + '\'' +
                        ", city='" + city + '\'' +
                        ", zone_id='" + zone_id + '\'' +
                        ", zone='" + zone + '\'' +
                        ", zone_code='" + zone_code + '\'' +
                        ", country_id='" + country_id + '\'' +
                        ", country='" + country + '\'' +
                        ", iso_code_2='" + iso_code_2 + '\'' +
                        ", iso_code_3='" + iso_code_3 + '\'' +
                        ", address_format='" + address_format + '\'' +
                        ", custom_field=" + custom_field +
                        '}';
            }
        }
}
