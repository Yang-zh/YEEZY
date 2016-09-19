package com.fangzhich.yeezy.base.data.net;

import com.blankj.utilcode.utils.EncryptUtils;
import com.fangzhich.yeezy.BuildConfig;
import com.fangzhich.yeezy.util.Constants;
import com.fangzhich.yeezy.util.ToastUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import rx.functions.Func1;

/**
 * BaseApi
 * Created by Khorium on 2016/9/14.
 */
public abstract class BaseApi {

    public final static String BASE_URL = BuildConfig.BASE_URL;

    private final static String APP_KEY = BuildConfig.APP_KEY;

    public static final String API_KEY = BuildConfig.API_KEY;

    /**
     * create Retrofit's service of class (not Authorized now)
     *
     * @param serviceClass class object
     * @param <S> class
     * @return Retrofit's service of class
     */
    protected static <S> S createClientAuthorizedService(Class<S> serviceClass) {
        return OauthServiceGenerator
                .createService(serviceClass);
    }

    /**
     * Handle HttpResult status.
     *
     * if everything ok, return concrete Entity in Result
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
        params.put("equipment_id", Constants.IMEI);

        ArrayList<Map.Entry<String,String>> paramList = new ArrayList<>(params.entrySet());

        //dict sort
        Collections.sort(paramList, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return (o1.getKey()).compareTo(o2.getKey());
            }
        });

        StringBuilder builder = new StringBuilder();

        for (Map.Entry entry:paramList) {
            builder.append(entry.getKey());
            builder.append(entry.getValue());
            ToastUtil.logTestError("param", (String) entry.getValue());
        }

        String origin = builder.toString();
        ToastUtil.logTestError("origin",origin);
        //MD5
        String MD5 = bytes2HexString(EncryptUtils.encryptMD5(origin.getBytes()));

        String SHA1 = bytes2HexString(EncryptUtils.encryptSHA1(MD5.getBytes()));
        ToastUtil.logTestError("SHA1",SHA1);

        return SHA1;
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
        params.add(Constants.IMEI);

        //dict sort
        Collections.sort(params);

        StringBuilder builder = new StringBuilder();

            for (String arg:params) {
                builder.append(arg);
            ToastUtil.logTestError("param",arg);
        }

        String origin = builder.toString();
        ToastUtil.logTestError("origin",origin);
        //MD5
        String MD5 = bytes2HexString(EncryptUtils.encryptMD5(origin.getBytes()));

        String SHA1 = bytes2HexString(EncryptUtils.encryptSHA1(MD5.getBytes()));
        ToastUtil.logTestError("SHA1",SHA1);

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
    protected static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis());
    }

}
