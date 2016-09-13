package com.fangzhich.yeezy.data.net;


import com.blankj.utilcode.utils.EncryptUtils;
import com.fangzhich.yeezy.YEEZY;
import com.fangzhich.yeezy.data.net.Bean.CategoryEntity;
import com.fangzhich.yeezy.data.net.Bean.ProductEntity;
import com.fangzhich.yeezy.data.net.framework.HttpResult;
import com.fangzhich.yeezy.data.net.Bean.LoginEntity;
import com.fangzhich.yeezy.data.net.Bean.RegisterEntity;
import com.fangzhich.yeezy.data.net.framework.HttpResultException;
import com.fangzhich.yeezy.data.net.framework.OauthServiceGenerator;
import com.fangzhich.yeezy.data.net.service.APIService;
import com.fangzhich.yeezy.util.LogUtils;

import java.util.ArrayList;
import java.util.Collections;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * NetClient
 * Created by Khorium on 2016/8/30.
 */
public class Api {

    public final static String BASE_URL = "http://192.168.0.105/";

    private final static String APP_KEY = "NDk4ZWNhOGNiZGExY2Y5MjhmYzdhYTljMzU2OTQwMDg";

    private static final String API_KEY = "ok9FUxp8YADCQyPmjfPthKcE9tkKHgoRXvFtzaPQAM9GVsIFZkpPZzfvc3I4bVywJxnIhkOwiF94EvpgsANpwbxtuTN0aVL18ro1oNtiMBqbPaiT4mLuljWc76jcip4ZijUsFHRnaQIdSfTKHJueVEdCaARwAf3XLZYcRWS5hafhAnSMujmkgdZcuOfpUGy0K8sUeTswEUiVBiBq8qzMzp8GpfKRdjJmgxEsjBOpsKVDEWbY4vaOW5lomG14KtxP";

    public final static int TIME_OUT = 5;

    /**
     * Methods will be used outside are static.
     */
    private Api() {}

    /**
     * create Retrofit's service of class (not Authorized now)
     *
     * @param serviceClass class object
     * @param <S> class
     * @return Retrofit's service of class
     */
    private static <S> S createClientAuthorizedService(Class<S> serviceClass) {
        return OauthServiceGenerator
                .createService(serviceClass);
    }

    /**
     * Login Request
     *
     * @param email email
     * @param password password
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void login(String email,String password,SingleSubscriber<LoginEntity> singleSubscriber) {
        String timestamp = getTimeStamp();
        String signature = getSignature(email,password,timestamp);

        Api.createClientAuthorizedService(APIService.class)
                .login(email,password,timestamp,signature,API_KEY,YEEZY.IMEI)
                .map(new HttpResultFunc<LoginEntity>())
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
    public static void register(String firstname, String lastname, String email, String password, SingleSubscriber<RegisterEntity> singleSubscriber) {
        String timestamp = getTimeStamp();
        String signature = getSignature(firstname,lastname,email,password,timestamp);

        Api.createClientAuthorizedService(APIService.class)
                .register(firstname,lastname,email,password,timestamp,signature,API_KEY,YEEZY.IMEI)
                .map(new HttpResultFunc<RegisterEntity>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }
    /**
     * Categories request
     *
     * @param page page
     * @param limit limit per page
     * @param parent_id category's parent_id
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void getCategories(int page, int limit, int parent_id, SingleSubscriber<ArrayList<CategoryEntity>> singleSubscriber) {
        String timestamp = getTimeStamp();
        String signature = getSignature(page,limit,parent_id,timestamp);
        createClientAuthorizedService(APIService.class)
                .getCategories(page,limit,parent_id,timestamp,signature,API_KEY,YEEZY.IMEI)
                .map(new HttpResultFunc<ArrayList<CategoryEntity>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    public static void getCategories(SingleSubscriber<ArrayList<CategoryEntity>> singleSubscriber) {
        getCategories(1,20,singleSubscriber);
    }

    public static void getCategories(int page, int limit, SingleSubscriber<ArrayList<CategoryEntity>> singleSubscriber) {
        getCategories(page,limit,0,singleSubscriber);
    }

    /**
     * Products request
     *
     * @param page page
     * @param limit limit per page
     * @param category_id product's category_id
     * @param singleSubscriber SingleSubscriber in RxJava (Callback)
     */
    public static void getProducts(int page, int limit, int category_id, SingleSubscriber<ArrayList<ProductEntity>> singleSubscriber) {
        String timestamp = getTimeStamp();
        String signature = getSignature(page,limit,category_id,timestamp);
        createClientAuthorizedService(APIService.class)
                .getProducts(page,limit,category_id,timestamp,signature,API_KEY,YEEZY.IMEI)
                .map(new HttpResultFunc<ArrayList<ProductEntity>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(singleSubscriber);
    }

    public static void getProducts(SingleSubscriber<ArrayList<ProductEntity>> singleSubscriber) {
        getProducts(1,20,singleSubscriber);
    }

    public static void getProducts(int page, int limit, SingleSubscriber<ArrayList<ProductEntity>> singleSubscriber) {
        getProducts(page,limit,0,singleSubscriber);
    }

    /**
     * Handle HttpResult status.
     *
     * if everything ok, return concrete Entity in Result
     * else throw HttpResultException with Message.
     *
     * @param <T>
     */
    protected static class HttpResultFunc<T> implements Func1<HttpResult<T>,T>{
        @Override
        public T call(HttpResult<T> HttpResult) {
            if (HttpResult.status_code!=0) {
                throw new HttpResultException(HttpResult.message);
            }
            return HttpResult.data;
        }
    }

    /**
     * get Signature of a concrete request
     *
     * @param args params of request
     * @return Signature
     */
    private static String getSignature(Object ...args) {
        ArrayList<String> params = new ArrayList<>();

        for (Object arg:args) {
            String param = arg instanceof String ? (String)arg : String.valueOf(arg);
            params.add(param);
        }
        params.add(API_KEY);
        params.add(APP_KEY);
        params.add(YEEZY.IMEI);

        //dict sort
        Collections.sort(params);

        StringBuilder builder = new StringBuilder();

        for (String arg:params) {
            builder.append(arg);
            LogUtils.getInstance().logTestError("param",arg);
        }

        String origin = builder.toString();
        LogUtils.getInstance().logTestError("origin",origin);
        //MD5
        String MD5 = bytes2HexString(EncryptUtils.encryptMD5(origin.getBytes()));

        String SHA1 = bytes2HexString(EncryptUtils.encryptSHA1(MD5.getBytes()));
        LogUtils.getInstance().logTestError("SHA1",SHA1);

        return SHA1;
    }

    private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    /**
     * byteArr转hexString
     *
     * @param bytes byte数组
     * @return 16进制字符串
     */
    public static String bytes2HexString(byte[] bytes) {
        char[] res = new char[bytes.length << 1];
        for (int i = 0, j = 0; i < bytes.length; i++) {
            res[j++] = hexDigits[bytes[i] >>> 4 & 0x0f];
            res[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(res);
    }

    /**
     * get Timestamp
     * @return timestamp
     */
    private static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis());
    }
}
