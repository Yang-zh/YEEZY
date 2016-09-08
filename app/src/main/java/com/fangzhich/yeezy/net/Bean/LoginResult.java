package com.fangzhich.yeezy.net.Bean;

/**
 *
 * Created by Khorium on 2016/9/8.
 */
public class LoginResult {

    /**
     * status : true
     * success : Login successful.
     * is_login : true
     * data : {"base":{"customer_id":"1","customer_group_id":"1","store_id":"0","language_id":"1","firstname":"cao","lastname":"wei","email":"admin@admin.com","telephone":"15088888888","fax":"","cart":null,"wishlist":null,"newsletter":"0","address_id":"1","custom_field":"","ip":"192.168.0.105","status":"1","approved":"1","safe":"0","token":"64e1b8d34f425d19e1ee2ea7236d3028","code":"","date_added":"2016-09-06 17:39:12"},"shipping_address":{"address_id":"1","firstname":"cao","lastname":"wei","company":"fangzhi","address_1":"chaoyangqu","address_2":"haidianqu","postcode":"100000","city":"sdfse","zone_id":"685","zone":"Beijing","zone_code":"BE","country_id":"44","country":"China","iso_code_2":"CN","iso_code_3":"CHN","address_format":"","custom_field":null}}
     */

    private boolean status;
    private String success;
    private boolean is_login;
    /**
     * base : {"customer_id":"1","customer_group_id":"1","store_id":"0","language_id":"1","firstname":"cao","lastname":"wei","email":"admin@admin.com","telephone":"15088888888","fax":"","cart":null,"wishlist":null,"newsletter":"0","address_id":"1","custom_field":"","ip":"192.168.0.105","status":"1","approved":"1","safe":"0","token":"64e1b8d34f425d19e1ee2ea7236d3028","code":"","date_added":"2016-09-06 17:39:12"}
     * shipping_address : {"address_id":"1","firstname":"cao","lastname":"wei","company":"fangzhi","address_1":"chaoyangqu","address_2":"haidianqu","postcode":"100000","city":"sdfse","zone_id":"685","zone":"Beijing","zone_code":"BE","country_id":"44","country":"China","iso_code_2":"CN","iso_code_3":"CHN","address_format":"","custom_field":null}
     */

    private DataBean data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public boolean isIs_login() {
        return is_login;
    }

    public void setIs_login(boolean is_login) {
        this.is_login = is_login;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * customer_id : 1
         * customer_group_id : 1
         * store_id : 0
         * language_id : 1
         * firstname : cao
         * lastname : wei
         * email : admin@admin.com
         * telephone : 15088888888
         * fax :
         * cart : null
         * wishlist : null
         * newsletter : 0
         * address_id : 1
         * custom_field :
         * ip : 192.168.0.105
         * status : 1
         * approved : 1
         * safe : 0
         * token : 64e1b8d34f425d19e1ee2ea7236d3028
         * code :
         * date_added : 2016-09-06 17:39:12
         */

        private BaseBean base;
        /**
         * address_id : 1
         * firstname : cao
         * lastname : wei
         * company : fangzhi
         * address_1 : chaoyangqu
         * address_2 : haidianqu
         * postcode : 100000
         * city : sdfse
         * zone_id : 685
         * zone : Beijing
         * zone_code : BE
         * country_id : 44
         * country : China
         * iso_code_2 : CN
         * iso_code_3 : CHN
         * address_format :
         * custom_field : null
         */

        private ShippingAddressBean shipping_address;

        public BaseBean getBase() {
            return base;
        }

        public void setBase(BaseBean base) {
            this.base = base;
        }

        public ShippingAddressBean getShipping_address() {
            return shipping_address;
        }

        public void setShipping_address(ShippingAddressBean shipping_address) {
            this.shipping_address = shipping_address;
        }

        public static class BaseBean {
            private String customer_id;
            private String customer_group_id;
            private String store_id;
            private String language_id;
            private String firstname;
            private String lastname;
            private String email;
            private String telephone;
            private String fax;
            private Object cart;
            private Object wishlist;
            private String newsletter;
            private String address_id;
            private String custom_field;
            private String ip;
            private String status;
            private String approved;
            private String safe;
            private String token;
            private String code;
            private String date_added;

            public String getCustomer_id() {
                return customer_id;
            }

            public void setCustomer_id(String customer_id) {
                this.customer_id = customer_id;
            }

            public String getCustomer_group_id() {
                return customer_group_id;
            }

            public void setCustomer_group_id(String customer_group_id) {
                this.customer_group_id = customer_group_id;
            }

            public String getStore_id() {
                return store_id;
            }

            public void setStore_id(String store_id) {
                this.store_id = store_id;
            }

            public String getLanguage_id() {
                return language_id;
            }

            public void setLanguage_id(String language_id) {
                this.language_id = language_id;
            }

            public String getFirstname() {
                return firstname;
            }

            public void setFirstname(String firstname) {
                this.firstname = firstname;
            }

            public String getLastname() {
                return lastname;
            }

            public void setLastname(String lastname) {
                this.lastname = lastname;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public String getFax() {
                return fax;
            }

            public void setFax(String fax) {
                this.fax = fax;
            }

            public Object getCart() {
                return cart;
            }

            public void setCart(Object cart) {
                this.cart = cart;
            }

            public Object getWishlist() {
                return wishlist;
            }

            public void setWishlist(Object wishlist) {
                this.wishlist = wishlist;
            }

            public String getNewsletter() {
                return newsletter;
            }

            public void setNewsletter(String newsletter) {
                this.newsletter = newsletter;
            }

            public String getAddress_id() {
                return address_id;
            }

            public void setAddress_id(String address_id) {
                this.address_id = address_id;
            }

            public String getCustom_field() {
                return custom_field;
            }

            public void setCustom_field(String custom_field) {
                this.custom_field = custom_field;
            }

            public String getIp() {
                return ip;
            }

            public void setIp(String ip) {
                this.ip = ip;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getApproved() {
                return approved;
            }

            public void setApproved(String approved) {
                this.approved = approved;
            }

            public String getSafe() {
                return safe;
            }

            public void setSafe(String safe) {
                this.safe = safe;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getDate_added() {
                return date_added;
            }

            public void setDate_added(String date_added) {
                this.date_added = date_added;
            }
        }

        public static class ShippingAddressBean {
            private String address_id;
            private String firstname;
            private String lastname;
            private String company;
            private String address_1;
            private String address_2;
            private String postcode;
            private String city;
            private String zone_id;
            private String zone;
            private String zone_code;
            private String country_id;
            private String country;
            private String iso_code_2;
            private String iso_code_3;
            private String address_format;
            private Object custom_field;

            public String getAddress_id() {
                return address_id;
            }

            public void setAddress_id(String address_id) {
                this.address_id = address_id;
            }

            public String getFirstname() {
                return firstname;
            }

            public void setFirstname(String firstname) {
                this.firstname = firstname;
            }

            public String getLastname() {
                return lastname;
            }

            public void setLastname(String lastname) {
                this.lastname = lastname;
            }

            public String getCompany() {
                return company;
            }

            public void setCompany(String company) {
                this.company = company;
            }

            public String getAddress_1() {
                return address_1;
            }

            public void setAddress_1(String address_1) {
                this.address_1 = address_1;
            }

            public String getAddress_2() {
                return address_2;
            }

            public void setAddress_2(String address_2) {
                this.address_2 = address_2;
            }

            public String getPostcode() {
                return postcode;
            }

            public void setPostcode(String postcode) {
                this.postcode = postcode;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getZone_id() {
                return zone_id;
            }

            public void setZone_id(String zone_id) {
                this.zone_id = zone_id;
            }

            public String getZone() {
                return zone;
            }

            public void setZone(String zone) {
                this.zone = zone;
            }

            public String getZone_code() {
                return zone_code;
            }

            public void setZone_code(String zone_code) {
                this.zone_code = zone_code;
            }

            public String getCountry_id() {
                return country_id;
            }

            public void setCountry_id(String country_id) {
                this.country_id = country_id;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getIso_code_2() {
                return iso_code_2;
            }

            public void setIso_code_2(String iso_code_2) {
                this.iso_code_2 = iso_code_2;
            }

            public String getIso_code_3() {
                return iso_code_3;
            }

            public void setIso_code_3(String iso_code_3) {
                this.iso_code_3 = iso_code_3;
            }

            public String getAddress_format() {
                return address_format;
            }

            public void setAddress_format(String address_format) {
                this.address_format = address_format;
            }

            public Object getCustom_field() {
                return custom_field;
            }

            public void setCustom_field(Object custom_field) {
                this.custom_field = custom_field;
            }
        }
    }
}
