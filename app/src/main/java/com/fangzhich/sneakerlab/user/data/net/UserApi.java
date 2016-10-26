package com.fangzhich.sneakerlab.user.data.net;


import com.fangzhich.sneakerlab.base.data.net.BaseApi;
import com.fangzhich.sneakerlab.main.data.entity.MessageEntity;
import com.fangzhich.sneakerlab.main.data.entity.NotificationEntity;
import com.fangzhich.sneakerlab.user.data.entity.CreditCardEntity;
import com.fangzhich.sneakerlab.user.data.entity.PersonalInfoEntity;
import com.fangzhich.sneakerlab.user.data.entity.UserInfoEntity;
import com.fangzhich.sneakerlab.user.data.entity.RegisterEntity;
import com.fangzhich.sneakerlab.user.data.entity.WishEntity;
import com.fangzhich.sneakerlab.util.Const;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * NetClient
 * Created by Khorium on 2016/8/30.
 */
public class UserApi extends BaseApi{

    /**
     * login Request
     *
     * @param email email
     * @param password password
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void login(String email,
                             String password,
                             SingleSubscriber<UserInfoEntity> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        params.put("timestamp", timestamp);

        String signature = getSignature(params);

        createService(UserService.class)
                .login(email,password,
                        timestamp,signature,API_KEY, Const.IMEI)
                .map(new HttpResultFunc<UserInfoEntity>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Login by facebook request
     *
     * @param accesstoken accesstoken
     * @param email email
     * @param firstname firstname
     * @param middlename middlename
     * @param lastname lastname
     * @param avatarimage avatarimage
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void loginByFacebook(String accesstoken, String facebookId, String email, String phone,
                                       String firstname, String middlename, String lastname, String avatarimage,
                             SingleSubscriber<UserInfoEntity> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String, String> params = new HashMap<>();
        params.put("accesstoken", accesstoken);
        params.put("facebookId", facebookId);
        params.put("email", email);
        params.put("phone", phone);
        params.put("firstname", firstname);
        params.put("middlename", middlename);
        params.put("lastname", lastname);
        params.put("timestamp", timestamp);

        String signature = getSignature(params);

        createService(UserService.class)
                .loginByFacebook(accesstoken,facebookId,email,phone,firstname,
                        middlename,lastname,avatarimage,
                        timestamp,signature,API_KEY, Const.IMEI)
                .map(new HttpResultFunc<UserInfoEntity>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Register request
     *
     * @param firstname firstName
     * @param lastname lastName
     * @param email email
     * @param password password
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void register(String firstname,
                                String lastname,
                                String email,
                                String password,
                                SingleSubscriber<RegisterEntity> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String, String> params = new HashMap<>();
        params.put("firstname", firstname);
        params.put("lastname", lastname);
        params.put("email", email);
        params.put("password", password);
        params.put("timestamp", timestamp);

        String signature = getSignature(params);

        createService(UserService.class)
                .register(firstname,lastname,email,password,
                        timestamp,signature,API_KEY, Const.IMEI)
                .map(new HttpResultFunc<RegisterEntity>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Sign out request
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void signOut(SingleSubscriber<Object> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("email",email);
        params.put("token",token);
        params.put("timestamp",timestamp);

        String signature = getSignature(params);

        createService(UserService.class)
                .signOut(email,token,
                        timestamp,signature,API_KEY,Const.IMEI)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Edit password request
     * @param old_password old password
     * @param new_password new password
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void editPassword(String old_password,
                                    String new_password,
                                    SingleSubscriber<Object> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("old_password",old_password);
        params.put("new_password",new_password);
        params.put("email",email);
        params.put("token",token);
        params.put("timestamp",timestamp);

        String signature = getSignature(params);

        createService(UserService.class)
                .editPassword(old_password, new_password,
                        email, token,
                        timestamp, signature, API_KEY,Const.IMEI)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Forget password request
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void forgetPassword(String email,SingleSubscriber<Object> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("email",email);
        params.put("timestamp",timestamp);

        String signature = getSignature(params);

        createService(UserService.class)
                .forgetPassword(email,
                        timestamp, signature, API_KEY,Const.IMEI)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Personal info request
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void getPersonalInfo(SingleSubscriber<PersonalInfoEntity> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("email",email);
        params.put("token",token);
        params.put("timestamp",timestamp);

        String signature = getSignature(params);

        createService(UserService.class)
                .getPersonalInfo(email,token,
                        timestamp,signature,API_KEY,Const.IMEI)
                .map(new HttpResultFunc<PersonalInfoEntity>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Edit personal info request
     * @param firstname firstname
     * @param lastname lastname
     * @param phone phone
     * @param sex sex
     * @param age age
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void editPersonalInfo(String firstname,
                                        String lastname,
                                        String phone,
                                        String sex,
                                        String age,
                                        SingleSubscriber<List> singleSubscriber) {

        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("firstname",firstname);
        params.put("lastname",lastname);
        params.put("phone",phone);
        params.put("sex",sex);
        params.put("age",age);
        params.put("email",email);
        params.put("token",token);
        params.put("timestamp",timestamp);

        String signature = getSignature(params);

        createService(UserService.class)
                .editPersonalInfo(firstname, lastname, phone, sex, age,
                        email, token,
                        timestamp, signature, API_KEY, Const.IMEI)
                .map(new HttpResultFunc<List>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    //--------------------------------------------------------------------------
    //---------------------------Credit Card------------------------------------
    //--------------------------------------------------------------------------

    /**
     * Get credit card info request
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void getCreditCard(SingleSubscriber<CreditCardEntity> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("email",email);
        params.put("token",token);
        params.put("timestamp",timestamp);

        String signature = getSignature(params);

        createService(UserService.class)
                .getCreditCard(email, token,
                        timestamp, signature, API_KEY,Const.IMEI)
                .map(new HttpResultFunc<CreditCardEntity>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Add credit card request
     * @param card_number credit card number
     * @param card_month credit card expiry month
     * @param card_year credit card expiry year
     * @param card_cvv credit card cvv
     * @param zip_code credit card postcode
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void addCreditCard(String card_number, String card_month, String card_year, String card_cvv, String zip_code, SingleSubscriber<String> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("card_number",card_number);
        params.put("card_month",card_month);
        params.put("card_year",card_year);
        params.put("card_cvv",card_cvv);
        params.put("zip_code",zip_code);
        params.put("email",email);
        params.put("token",token);
        params.put("timestamp",timestamp);

        String signature = getSignature(params);

        createService(UserService.class)
                .addCreditCard(card_number,card_month,card_year,card_cvv,zip_code,
                        email, token,
                        timestamp, signature, API_KEY,Const.IMEI)
                .map(new HttpResultFunc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Edit credit card request
     * @param card_number credit card number
     * @param card_month credit card expiry month
     * @param card_year credit card expiry year
     * @param card_cvv credit card cvv
     * @param zip_code credit card postcode
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void editCreditCard(String card_number, String card_month, String card_year, String card_cvv, String zip_code, SingleSubscriber<Object> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("card_number",card_number);
        params.put("card_month",card_month);
        params.put("card_year",card_year);
        params.put("card_cvv",card_cvv);
        params.put("zip_code",zip_code);
        params.put("email",email);
        params.put("token",token);
        params.put("timestamp",timestamp);

        String signature = getSignature(params);

        createService(UserService.class)
                .editCreditCard(card_number,card_month,card_year,card_cvv,zip_code,
                        email, token,
                        timestamp, signature, API_KEY,Const.IMEI)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    //--------------------------------------------------------------------------
    //---------------------------Address----------------------------------------
    //--------------------------------------------------------------------------

    /**
     * Add address request
     * @param fullname fullname
     * @param phone phone
     * @param address address
     * @param suite suite
     * @param city city
     * @param postcode postcode
     * @param country_id country_id
     * @param zone_id zone_id
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void addAddress(String fullname, String phone, String address, String suite,
                                  String city, String postcode, String country_id, String zone_id,
                                  SingleSubscriber<String> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("fullname",fullname);
        params.put("phone",phone);
        params.put("address",address);
        params.put("suite",suite);
        params.put("city",city);
        params.put("postcode",postcode);
        params.put("country_id",country_id);
        params.put("zone_id",zone_id);
        params.put("email",email);
        params.put("token",token);
        params.put("timestamp",timestamp);

        String signature = getSignature(params);

        createService(UserService.class)
                .addAddress(fullname,phone,address,suite,city,postcode,country_id,zone_id,
                        email, token,
                        timestamp, signature, API_KEY,Const.IMEI)
                .map(new HttpResultFunc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Edit address request
     * @param address_id address_id
     * @param fullname fullname
     * @param phone phone
     * @param address address
     * @param suite suite
     * @param city city
     * @param postcode postcode
     * @param country_id country_id
     * @param zone_id zone_id
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void editAddress(String address_id,
                                   String fullname, String phone, String address, String suite,
                                   String city, String postcode, String country_id, String zone_id,
                                   SingleSubscriber<Object> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("address_id",address_id);
        params.put("fullname",fullname);
        params.put("phone",phone);
        params.put("address",address);
        params.put("suite",suite);
        params.put("city",city);
        params.put("postcode",postcode);
        params.put("country_id",country_id);
        params.put("zone_id",zone_id);
        params.put("email",email);
        params.put("token",token);
        params.put("timestamp",timestamp);

        String signature = getSignature(params);

        createService(UserService.class)
                .editAddress(address_id,fullname,phone,address,suite,city,postcode,country_id,zone_id,
                        email, token,
                        timestamp, signature, API_KEY,Const.IMEI)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    //--------------------------------------------------------------------------
    //------------------------------WishList------------------------------------
    //--------------------------------------------------------------------------

    /**
     * Get wish list request
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void getWishList(SingleSubscriber<ArrayList<WishEntity>> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("email",email);
        params.put("token",token);
        params.put("timestamp",timestamp);

        String signature = getSignature(params);

        createService(UserService.class)
                .getWishList(email, token,
                        timestamp, signature, API_KEY,Const.IMEI)
                .map(new HttpResultFunc<ArrayList<WishEntity>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Add product in wish list request
     * @param product_id product_id
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void addWish(String product_id,
                               SingleSubscriber<Object> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("product_id",product_id);
        params.put("email",email);
        params.put("token",token);
        params.put("timestamp",timestamp);

        String signature = getSignature(params);

        createService(UserService.class)
                .addWish(product_id,
                        email, token,
                        timestamp, signature, API_KEY,Const.IMEI)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Delete product in wish list request
     * @param product_id product_id
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void deleteWish(String product_id,
                               SingleSubscriber<Object> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("product_id",product_id);
        params.put("email",email);
        params.put("token",token);
        params.put("timestamp",timestamp);

        String signature = getSignature(params);

        createService(UserService.class)
                .deleteWish(product_id,
                        email, token,
                        timestamp, signature, API_KEY,Const.IMEI)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Share product request
     * @param product_id product_id
     * @param channel channel
     * @param text text
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void share(String product_id, String channel, String text,
                                  SingleSubscriber<Object> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("product_id",product_id);
        params.put("channel",channel);
        params.put("text",text);
        params.put("email",email);
        params.put("token",token);
        params.put("timestamp",timestamp);

        String signature = getSignature(params);

        createService(UserService.class)
                .share(product_id,channel,text,
                        email, token,
                        timestamp, signature, API_KEY,Const.IMEI)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Get notification list request
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void getNotificationList(SingleSubscriber<ArrayList<NotificationEntity>> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("email",email);
        params.put("token",token);
        params.put("timestamp",timestamp);

        String signature = getSignature(params);

        createService(UserService.class)
                .getNotificationList(email, token,
                        timestamp, signature, API_KEY,Const.IMEI)
                .map(new HttpResultFunc<ArrayList<NotificationEntity>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Customer support request
     * @param text text
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void sendSupportMessage(String type, String text, SingleSubscriber<Object> singleSubscriber) {
        String timestamp = getTimeStamp();
        String fullname = Const.getUserInfo()==null?Const.getUserInfo().user_info.firstname+Const.getUserInfo().user_info.lastname:"unknownUser";

        HashMap<String,String> params = new HashMap<>();
        params.put("type",type);
        params.put("fullname",fullname);
        params.put("text",text);
        params.put("equipment_token",Const.fireBaseMessageToken);
        params.put("email",email);
        params.put("token",token);
        params.put("timestamp",timestamp);

        String signature = getSignature(params);

        createService(UserService.class)
                .requestSupport(type,fullname,text,Const.fireBaseMessageToken,
                        email, token,
                        timestamp, signature, API_KEY,Const.IMEI)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    /**
     * Support message list request
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void getSupportMessageList(SingleSubscriber<ArrayList<MessageEntity>> singleSubscriber) {
        String timestamp = getTimeStamp();

        HashMap<String,String> params = new HashMap<>();
        params.put("equipment_token",Const.fireBaseMessageToken);
        params.put("email",email);
        params.put("token",token);
        params.put("timestamp",timestamp);

        String signature = getSignature(params);

        createService(UserService.class)
                .getSupportMessageList(Const.fireBaseMessageToken,
                        email, token,
                        timestamp, signature, API_KEY,Const.IMEI)
                .map(new HttpResultFunc<ArrayList<MessageEntity>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }
}


