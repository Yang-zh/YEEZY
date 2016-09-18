package com.fangzhich.yeezy.base.data.net;

import com.blankj.utilcode.utils.EncryptUtils;
import com.fangzhich.yeezy.BuildConfig;
import com.fangzhich.yeezy.YEEZY;
import com.fangzhich.yeezy.util.LogUtils;

import java.util.ArrayList;
import java.util.Collections;

import rx.functions.Func1;

/**
 * BaseApi
 * Created by Khorium on 2016/9/14.
 */
public abstract class BaseApi {

    public final static String BASE_URL = BuildConfig.BASE_URL;

    private final static String APP_KEY = BuildConfig.APP_KEY;

    public static final String API_KEY = BuildConfig.API_KEY;

    public final static int TIME_OUT = 5;

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
     * @param args params of request
     * @return Signature
     */
    protected static String getSignature(Object ...args) {
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
            LogUtils.logTestError("param",arg);
        }

        String origin = builder.toString();
        LogUtils.logTestError("origin",origin);
        //MD5
        String MD5 = bytes2HexString(EncryptUtils.encryptMD5(origin.getBytes()));

        String SHA1 = bytes2HexString(EncryptUtils.encryptSHA1(MD5.getBytes()));
        LogUtils.logTestError("SHA1",SHA1);

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
