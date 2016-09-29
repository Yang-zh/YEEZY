package com.fangzhich.yeezy.base.data.net;

import com.blankj.utilcode.utils.EncryptUtils;
import com.fangzhich.yeezy.BuildConfig;
import com.fangzhich.yeezy.util.Const;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import rx.functions.Func1;
import timber.log.Timber;

/**
 * BaseApi
 * Created by Khorium on 2016/9/14.
 */
public abstract class BaseApi {

    protected static String email = Const.getUserInfo()==null?null:Const.getUserInfo().user_info.email;
    protected static String token = Const.getUserInfo()==null?null:Const.getUserInfo().token;

    public static void refreshLoginInfo() {
        email =  Const.getUserInfo()==null?null:Const.getUserInfo().user_info.email;
        token = Const.getUserInfo()==null?null:Const.getUserInfo().token;
    }

    final static String BASE_URL = BuildConfig.BASE_URL;

    private final static String APP_KEY = BuildConfig.APP_KEY;

    protected static final String API_KEY = BuildConfig.API_KEY;

    private static HashMap<Class,Object> services = new HashMap<>();

    /**
     * create Retrofit's service of class
     *
     * @param serviceClass class object
     * @param <S> class
     * @return Retrofit's service of class
     */
    protected static <S> S createService(Class<S> serviceClass) {
        S service = isServiceExist(serviceClass);
        if (service == null) {
            service = ServiceGenerator.createService(serviceClass);
            services.put(serviceClass,service);
        }
        return service;
    }

    /**
     * create Retrofit's Authorized service of class
     *
     * @param serviceClass class object
     * @param <S> class
     * @return Retrofit's service of class
     */
    protected static <S> S createClientAuthorizedService(Class<S> serviceClass) {
        return ServiceGenerator.createService(serviceClass);
    }

    /**
     * check if service created
     * @param serviceClass service class need to be checked
     * @param <S> service
     * @return existing service or null
     */
    private static <S> S isServiceExist(Class<S> serviceClass) {
        for (Map.Entry serviceSet:services.entrySet()) {
            if (serviceSet.getKey().equals(serviceClass)) {
                //todo how to manage unknown amount kinds of objects?
                return (S) serviceSet.getValue();
            }
        }
        return null;
    }

    /**
     * Handle HttpResult status.
     *
     * if everything ok, return concrete Entity in Result,
     * else throw HttpResultException with Message.
     *
     * @param <T>
     */
    public static class HttpResultFunc<T> implements Func1<HttpResult<T>,T> {
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
     * @param params params of request in HashMap
     * @return Signature
     */

    protected static String getSignature(HashMap<String, String> params) {
        params.put("apiKey",API_KEY);
        params.put("appKey",APP_KEY);
        params.put("equipment_id", Const.IMEI);

        ArrayList<Map.Entry<String,String>> paramList = new ArrayList<>(params.entrySet());

        //dict sort
        Collections.sort(paramList, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });

        StringBuilder builder = new StringBuilder();

        for (Map.Entry entry:paramList) {
            builder.append(entry.getKey());

            if (entry.getValue()!=null) {
                builder.append(entry.getValue());
            }
        }

        String origin = builder.toString();
        Timber.d(origin);
        //MD5
        String MD5 = bytes2HexString(EncryptUtils.encryptMD5(origin.getBytes()));


        return bytes2HexString(EncryptUtils.encryptSHA1(MD5.getBytes()));
    }

    /**
     * get Signature of a concrete request
     *
     * @param args params of request in String[]
     * @return Signature
     */
    @Deprecated
    protected static String getSignature(String ...args) {
        ArrayList<String> params = new ArrayList<>();

        Collections.addAll(params, args);
        params.add(API_KEY);
        params.add(APP_KEY);
        params.add(Const.IMEI);

        //dict sort
        Collections.sort(params);

        StringBuilder builder = new StringBuilder();

            for (String arg:params) {
                builder.append(arg);
        }

        String origin = builder.toString();
        //MD5
        String MD5 = bytes2HexString(EncryptUtils.encryptMD5(origin.getBytes()));

        return bytes2HexString(EncryptUtils.encryptSHA1(MD5.getBytes()));
    }

    private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    /**
     * byteArr转hexString
     *
     * @param bytes byte数组
     * @return 16进制字符串
     */
    private static String bytes2HexString(byte[] bytes) {
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
    protected static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis());
    }

}
